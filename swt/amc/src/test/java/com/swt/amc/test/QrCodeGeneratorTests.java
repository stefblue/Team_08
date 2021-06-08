package com.swt.amc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.swt.amc.interfaces.IQrCodeGenerator;

@SpringBootTest()
public class QrCodeGeneratorTests {

	@Autowired
	private Optional<IQrCodeGenerator> qrCodeGenerator;

	@Test
	public void generateQrCode() throws NotFoundException {
		assertNotNull(qrCodeGenerator.get());
		BufferedImage qrCode = qrCodeGenerator.get().generateQrCode("qrCodeText");
		LuminanceSource ls = new BufferedImageLuminanceSource(qrCode);
		BinaryBitmap bm = new BinaryBitmap(new HybridBinarizer(ls));
		Result qrCodeContent = new MultiFormatReader().decode(bm);
		assertEquals(qrCodeContent.getText(), "qrCodeText");
	}

}
