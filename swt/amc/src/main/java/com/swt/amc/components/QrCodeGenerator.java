package com.swt.amc.components;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.swt.amc.interfaces.IQrCodeGenerator;

@Component
public class QrCodeGenerator implements IQrCodeGenerator {

	@Override
	public BufferedImage generateQrCode(String qrCodeContent) {
		QRCodeWriter writer = new QRCodeWriter();
		try {
			return MatrixToImageWriter.toBufferedImage(writer.encode(qrCodeContent, BarcodeFormat.QR_CODE, 200, 200));
		} catch (WriterException e) {
			// TODO FIXME FL!
			return null;
		}
	}
}
