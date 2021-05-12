package com.swt.amc.test;

import javafx.util.Pair;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.components.QrCodeLinkVerifier;
import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.rest.controller.AmcRestController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class AmcRestControllerTests {

    @Autowired
    private AmcRestController rest;

    @Test
    public void verifyQrCodeViaApp_containsElements() throws AmcException {
        LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

        Assert.assertEquals(5, lecture.getDates().size());
    }
    @Test
    public void verifyQrCodeViaApp_elementsAreDates() throws AmcException {
        LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

        Assert.assertTrue(lecture.getDates().getFirst() instanceof Pair);
        Assert.assertTrue(lecture.getDates().getFirst().getKey() instanceof LocalDateTime);
        Assert.assertTrue(lecture.getDates().getFirst().getValue() instanceof Duration);
    }
}
