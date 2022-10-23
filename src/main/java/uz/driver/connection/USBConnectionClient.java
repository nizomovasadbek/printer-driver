package uz.driver.connection;

import sun.nio.cs.US_ASCII;
import uz.driver.commands.label.TSPLLabel;
import uz.driver.exceptions.ConnectionClientException;
import uz.driver.exceptions.PrinterException;

import javax.usb.*;
import javax.usb.event.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class USBConnectionClient extends AbstractConnectionClient implements TSPLConnectionClient {

    private short vendorId;
    private short productId;
    private short outPipeAddress = -126;
    private short inPipeAddress = 1;
    private UsbDevice usbDevice;
    private UsbInterface usbInterface;
    private UsbPipe writePipe;
    private UsbPipe readPipe;

    public USBConnectionClient(short vendorId){
        this.vendorId = vendorId;
    }

    public USBConnectionClient(short vendorId, short productId){
        this.vendorId = vendorId;
        this.productId = productId;
    }

    @Override
    public void init() {
        try{
            final UsbServices services = UsbHostManager.getUsbServices();
            System.out.printf("USB Service Implementation: %s\n", services.getImpDescription());
            System.out.printf("Implementation version: %s\n", services.getImpVersion());
            System.out.printf("Service API version: %s\n", services.getApiVersion());

            if(productId == 0){
                usbDevice = findAndGetDeviceByVendor(services.getRootUsbHub(), vendorId);
            } else {
                usbDevice = findDevice(services.getRootUsbHub(), vendorId, productId);
            }

            findAndClaimInterface();
        } catch(UsbException e){
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        if(isConnected)
            return;
        if(usbInterface == null)
            throw new PrinterException("Interface has to be claimed before attempting connection");

        readPipe = getReadPipe();
        writePipe = getWritePipe();

        try{
            readPipe.open();
            writePipe.open();
            isConnected = true;

            notifyConnection();
        }catch (UsbException e){
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if(!isConnected)
            return;

        try{
            writePipe.close();
            readPipe.close();

            isConnected = false;
            notifyConnectionLost();
        }catch (UsbException e){
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        try{
            usbInterface.release();
            usbInterface = null;
        }catch(UsbException e){
            e.printStackTrace();
        }
    }

    @Override
    public void send(String tsplMessage) {
        send(tsplMessage.getBytes(StandardCharsets.US_ASCII));
    }

    @Override
    public void send(TSPLLabel label) {
        send(label.getTsplCode());
    }

    private void send(byte[] message){
        if(!isConnected)
            throw new PrinterException("Printer is not connected");

        try{
            writePipe.syncSubmit(message);
        }catch(UsbException e){
            e.printStackTrace();
        }
    }

    public void usbDeviceDetached(UsbDeviceEvent event){
        clientListeners.forEach((clientListener) -> listenerExecutorService.execute(() ->
                    clientListener.connectionLost(USBConnectionClient.this)
                ));
    }

    public void errorEventOccured(UsbDeviceErrorEvent event){

    }

    public void dataEventOccured(UsbDeviceDataEvent event){

    }

    @SuppressWarnings("unchecked")
    private UsbDevice findAndGetDeviceByVendor(UsbHub hub, short vendorId){
        for(UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()){
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if(desc.idVendor() == vendorId){
                productId = desc.idProduct();
                return device;
            }
            if(device.isUsbHub()){
                device = findAndGetDeviceByVendor((UsbHub) device, vendorId);
                if(device != null){
                    productId = desc.idProduct();
                    return device;
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static UsbDevice findDevice(UsbHub hub, short vendorId, short productId){
        for(UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()){
            UsbDeviceDescriptor desc = hub.getUsbDeviceDescriptor();
            if(desc.idVendor() == vendorId && desc.idProduct() == productId){
                return device;
            }
            if(device.isUsbHub()){
                device = findDevice((UsbHub) device, vendorId, productId);
                if(device != null)
                    return device;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void findAndClaimInterface(){
        UsbConfiguration configuration = usbDevice.getActiveUsbConfiguration();
        usbInterface = configuration.getUsbInterface((byte) 0);
        try{
            usbInterface.claim(usbInterface1 -> true);
        }catch (UsbException e){
            e.printStackTrace();
        }

        ((List<UsbEndpoint>) usbInterface.getUsbEndpoints()).forEach(p ->
                System.err.printf("Interface Direction %s, Interface Address: %s\n",
                        p.getDirection(), p.getUsbEndpointDescriptor().bEndpointAddress())
        );


    }

    private UsbPipe getReadPipe(){
        UsbPipe localReadPipe = usbInterface.getUsbEndpoint((byte) outPipeAddress).getUsbPipe();
        localReadPipe.addUsbPipeListener(new UsbPipeListener() {
            @Override
            public void errorEventOccurred(UsbPipeErrorEvent usbPipeErrorEvent) {

            }

            @Override
            public void dataEventOccurred(UsbPipeDataEvent usbPipeDataEvent) {
                notifyMessageReceived(new String(usbPipeDataEvent.getData(), StandardCharsets.US_ASCII));
            }
        });

        return localReadPipe;
    }

    private UsbPipe getWritePipe(){
        UsbPipe localWritePipe = usbInterface.getUsbEndpoint((byte) inPipeAddress).getUsbPipe();
        localWritePipe.addUsbPipeListener(new UsbPipeListener() {
            @Override
            public void errorEventOccurred(UsbPipeErrorEvent usbPipeErrorEvent) {
                notifyMessageSendFailed(new ConnectionClientException(
                        usbPipeErrorEvent.getUsbException().getMessage()
                ), "");
            }

            @Override
            public void dataEventOccurred(UsbPipeDataEvent usbPipeDataEvent) {
                notifyMessageSent(new String(usbPipeDataEvent.getData(), StandardCharsets.US_ASCII));
            }
        });

        return localWritePipe;
    }

}
