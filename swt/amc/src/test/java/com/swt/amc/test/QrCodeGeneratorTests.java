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
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IAmcComponent;

@SpringBootTest()
public class QrCodeGeneratorTests {

	@Autowired
	private Optional<IAmcComponent> amcComponent;

	@Test
	public void generateQrCode() throws NotFoundException, AmcException {
		assertNotNull(amcComponent.get());
		BufferedImage qrCode = amcComponent.get().generateQrCode("qrCodeText");
		LuminanceSource ls = new BufferedImageLuminanceSource(qrCode);
		BinaryBitmap bm = new BinaryBitmap(new HybridBinarizer(ls));
		Result qrCodeContent = new MultiFormatReader().decode(bm);
		assertEquals(qrCodeContent.getText(), "qrCodeText");
	}

}
