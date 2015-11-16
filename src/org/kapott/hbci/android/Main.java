package org.kapott.hbci.android;

import org.kapott.hbci.exceptions.HBCI_Exception;
import org.kapott.hbci.exceptions.InvalidUserDataException;
import org.kapott.hbci.manager.HBCIUtilsInternal;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by alex on 16.11.2015.
 */
public class Main {
    public Main() {
        String inPath = "C://Users/alex/hbci/";
        String outPath = inPath + "android/";
        String filename = "hbci-plus.xml";
        File file = new File(inPath + filename);
        try {
            InputStream syntaxFileStream = new FileInputStream(file);
            if (syntaxFileStream == null) {
                throw new InvalidUserDataException(
                        HBCIUtilsInternal.getLocMsg("EXCMSG_KRNL_CANTLOAD_SYN", 0));
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            dbf.setValidating(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document syntax = db.parse(syntaxFileStream);
            syntaxFileStream.close();
            //
            //
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(syntax);
            File outFile = new File(outPath + filename);
            StreamResult result = new StreamResult(outFile);
            transformer.transform(source, result);
        } catch (FactoryConfigurationError e) {
            throw new HBCI_Exception(HBCIUtilsInternal.getLocMsg("EXCMSG_MSGGEN_DBFAC"), e);
        } catch (ParserConfigurationException e) {
            throw new HBCI_Exception(HBCIUtilsInternal.getLocMsg("EXCMSG_MSGGEN_DB"), e);
        } catch (Exception e) {
            throw new HBCI_Exception(HBCIUtilsInternal.getLocMsg("EXCMSG_MSGGEN_STXFILE"), e);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

