package com.ibm.rpc_starter.serialize.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.ibm.rpc_starter.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/13:48
 * @Description: 序列化工具类（Hessian序列化协议
 */
@Slf4j
public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            log.error("serialize io exception,object=" + obj, ioe);
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException ioe) {
                    log.error("serialize byteArrayOutputStream close io exception,object=" + obj, ioe);
                }
            }

            if (hessianOutput != null) {
                try {
                    hessianOutput.close();
                } catch (IOException ioe) {
                    log.error("serialize hessianOutput close io exception,object=" + obj, ioe);
                }
            }
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            hessianInput = new HessianInput(byteArrayInputStream);
            Object o=hessianInput.readObject(clazz);
            return clazz.cast(o);
        } catch (IOException ioe) {
            log.error("deserialize io exception,bytes=" + bytes, ioe);
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException ioe) {
                    log.error("deserialize byteArrayOutputStream close io exception,bytes=" + bytes, ioe);
                }
            }
            if (hessianInput != null) {
                try {
                    hessianInput.close();
                } catch (Exception ioe) {
                    log.error("deserialize hessianInput close io exception,bytes=" + bytes, ioe);
                }
            }
        }
        return null;
    }
}
