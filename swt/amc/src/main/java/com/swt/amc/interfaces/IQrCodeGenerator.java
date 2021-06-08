package com.swt.amc.interfaces;

import java.awt.image.BufferedImage;

public interface IQrCodeGenerator {

	BufferedImage generateQrCode(String qrCodeContent);

}
