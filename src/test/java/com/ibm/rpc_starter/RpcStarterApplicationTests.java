package com.ibm.rpc_starter;

import com.ibm.rpc_starter.common.extension.ExtensionLoader;
import com.ibm.rpc_starter.serialize.Serializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcStarterApplicationTests {

    @Test
    void contextLoads() {
        Serializer serializer=ExtensionLoader.getExtensionLoader(Serializer.class)
                .getExtension("hessian");
    }

}
