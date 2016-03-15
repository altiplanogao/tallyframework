package com.taoswork.tallybook.module.base;

import com.taoswork.tallybook.module.IModuleCenter;
import com.taoswork.tallybook.module.ModuleCenterInfo;
import com.taoswork.tallybook.module.ModuleInfo;
import com.taoswork.tallybook.module.io.AssetCategory;
import com.taoswork.tallybook.module.io.AssetFacet;
import com.taoswork.tallybook.module.IModule;
import com.taoswork.tallybook.module.io.Sheet;
import com.taoswork.tallybook.module.security.KeyUtility;
import com.taoswork.tallybook.module.support.IAssetCategory;
import com.taoswork.tallybook.module.support.IAssetFacet;
import com.taoswork.tallybook.module.support.ISheet;
import org.apache.commons.lang3.SerializationUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public abstract class BaseModule implements IModule {

    private final Map<String, Sheet> sheetsCache;
    private final Map<String, AssetCategory> categoryCache;
    private final Map<String, AssetFacet> facetCache;

    public BaseModule() {
        {
            Collection<ISheet> sheets = makeSheets();
            if (sheets == null || sheets.isEmpty()) {
                sheetsCache = null;
            } else {
                Map<String, Sheet> localSheets = new HashMap<String, Sheet>();
                for (ISheet isheet : sheets) {
                    final Sheet sheet = (isheet instanceof Sheet) ?
                            (Sheet) isheet : new Sheet(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                sheetsCache = Collections.unmodifiableMap(localSheets);
            }
        }
        {
            Collection<IAssetCategory> sheets = makeCategories();
            if (sheets == null || sheets.isEmpty()) {
                categoryCache = null;
            } else {
                Map<String, AssetCategory> localSheets = new HashMap<String, AssetCategory>();
                for (IAssetCategory isheet : sheets) {
                    final AssetCategory sheet = (isheet instanceof AssetCategory) ?
                            (AssetCategory) isheet : new AssetCategory(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                categoryCache = Collections.unmodifiableMap(localSheets);
            }
        }
        {
            Collection<IAssetFacet> sheets = makeFacets();
            if (sheets == null || sheets.isEmpty()) {
                facetCache = null;
            } else {
                Map<String, AssetFacet> localSheets = new HashMap<String, AssetFacet>();
                for (IAssetFacet isheet : sheets) {
                    final AssetFacet sheet = (isheet instanceof AssetFacet) ?
                            (AssetFacet) isheet : new AssetFacet(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                facetCache = Collections.unmodifiableMap(localSheets);
            }
        }
    }

    protected abstract Collection<ISheet> makeSheets();
    protected abstract Collection<IAssetCategory> makeCategories();
    protected abstract Collection<IAssetFacet> makeFacets();

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getFullName() {
        return this.getClass().getName();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getProducer() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public Date getUpdateDate() {
        return null;
    }

    @Override
    public Collection<String> getCategories() {
        return categoryCache.keySet();
    }

    @Override
    public AssetCategory getCategory(String category) {
        return categoryCache.get(category);
    }

    @Override
    public Collection<String> getSheets() {
        return sheetsCache.keySet();
    }

    @Override
    public Sheet getSheet(String sheet) {
        return sheetsCache.get(sheet);
    }

    @Override
    public AssetFacet getFacet(String facet) {
        return facetCache.get(facet);
    }
//
//    public static final String CIPHER_ALGORITHM = "RSA";
//
//    private String privateKey(){
//        return "";
//    };
//
//    private static class VContent implements Serializable{
//        public String name;
//        public int content;
//
//        public VContent(String name, int content) {
//            this.name = name;
//            this.content = content;
//        }
//    }
//
//    public static boolean moduleRequestVerify(IModuleCenter center, String hereName, String herePrivate, String therePublic) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        int rand = new Random().nextInt(Integer.MAX_VALUE - 1);
//        VContent vContent = new VContent(hereName, rand);
//
//        byte[] randBytes = SerializationUtils.serialize(vContent);
////        Conversion.intToByteArray(rand, 0, randBytes, 0, 4);
//        Cipher hereCipher = Cipher.getInstance(CIPHER_ALGORITHM);
//        Key herePrivateKey = KeyUtility.getPrivateKey(CIPHER_ALGORITHM, herePrivate);
//        hereCipher.init(Cipher.ENCRYPT_MODE, herePrivateKey);
//        byte[] hereBytes = hereCipher.doFinal(randBytes);
//
//        Cipher thereCiper = Cipher.getInstance(CIPHER_ALGORITHM);
//        Key therePublicKey = KeyUtility.getPubKey(CIPHER_ALGORITHM, therePublic);
//        thereCiper.init(Cipher.ENCRYPT_MODE, therePublicKey);
//        byte[] thereBytes = thereCiper.doFinal(hereBytes);
//
//
//    }
}
