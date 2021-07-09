/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2016-5-12
 */

package org.sniper.security.algorithm.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sniper.codec.Base64Codec;
import org.sniper.codec.Codec;
import org.sniper.codec.CompositeCodec;
import org.sniper.codec.HexCodec;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.security.algorithm.asymmetric.RSAAlgorithm;

/**
 * 非对称加解密算法单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AsymmetricAlgorithmTest {
	
	private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD6jUwellZWDops/Q77viRj2pu4kElvKmn2eo70RxSc7vVSXI2fviQjk42re3hSLI+vhAoUOAEHW1+mOHpAcjT96cIsgyGAtltWy4HpsgjeLfHfl3stNTkS1krk8vsa0ndllNEKiq5n4FgjCW0KO+Y5I9z1O8Wy3zGHWkSTRhGOkQIDAQAB";
	
	private String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPqNTB6WVlYOimz9Dvu+JGPam7iQSW8qafZ6jvRHFJzu9VJcjZ++JCOTjat7eFIsj6+EChQ4AQdbX6Y4ekByNP3pwiyDIYC2W1bLgemyCN4t8d+Xey01ORLWSuTy+xrSd2WU0QqKrmfgWCMJbQo75jkj3PU7xbLfMYdaRJNGEY6RAgMBAAECgYEA9vBsJXlmfkq2g9Sl/9fXZdL/x1fuCHJ1HUiNvJSR7FpXz2UI8HgmhinXZzu5ysPAmew5QtQvr2CPVPQ1CW+GaQfP8dbGDG6qnEKr2jbX6nit0ibdzCxa6I019KWXy3RJuNDfOeiygA4+4bw6DZOmeczGeYG6mDrazVCtSLJp50ECQQD+XsCnfjT3c3WZFt5XwHlwq6OQxbnazqiQLXLEUumb+oPkdFs1UiYGUN0G2Whp7f2WCc7BNXImHg03R/jbS1EHAkEA/ChIISvP8awJmiFey0buBu2KIgpJJJzvqd7JRDtOpvZ05JU0+j/gchkXvNavRd+TLbBf9lSKk/ebMWcp/Hz1pwJBAIrWtvr05P4ndAmSNNvav64i0Q1FDxOB9vlzBRBpXP749qJsbBgndX+/BVUmt1oSP3Z0dfh5LovlnTsOOqOfcWMCQHrTWEcoIzNSHJ9CT/1ugHVJcbahEf3OoAM7rQgaZ3Z2qHvLnjGjub16MZDfdiLwA64Gsse3aIUo4CSRXudJKxMCQQCj7aLx7TqL1AwHmsuaqtvGstgvOTe9znIFfuqwOp1wQXf1/ArUZFSpNbZrbwodhr2192MA+uUel3gWbIQX7Rbz";
	
	private String plaintext = "杜斌_dub727@163.com";
	
	private Codec resultCodec;
	
	private Codec keyCodec;
	
	@Before
	public void before() {
		/* 依次按16进制和Base64对加密结果进行编码，解密时解码的顺序相反 */
		List<Codec> members = CollectionUtils.newArrayList();
		Base64Codec base64Codec = new Base64Codec();
		members.add(new HexCodec());
		members.add(base64Codec);
		this.resultCodec = new CompositeCodec(members);
		
		// 由于公钥和私钥的字符串为Base64编码格式的，因此在进行RSA加解密时，公钥和私钥只能用Base64来解码
		this.keyCodec = base64Codec;
	}
	
	@Test
	public void testRSAAlgorithm() throws Exception {
		RSAAlgorithm rsaAlgorithm = new RSAAlgorithm();
		rsaAlgorithm.setPublicKey(publicKey);
		rsaAlgorithm.setPrivateKey(privateKey);
		rsaAlgorithm.setResultCodec(resultCodec);
		rsaAlgorithm.setKeyCodec(keyCodec);
		
		rsaAlgorithm.afterPropertiesSet();
		String algorithm = rsaAlgorithm.getAlgorithm();
		System.out.println(algorithm + "公钥:" + rsaAlgorithm.getPublicKey());
		System.out.println(algorithm + "私钥:" + rsaAlgorithm.getPrivateKey());

		String ciphertext = rsaAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = rsaAlgorithm.decrypt(ciphertext);
		System.out.println("RSA密文:" + ciphertext);
		System.out.println("RSA明文:" + decryptedPlaintext);
	}
	
}
