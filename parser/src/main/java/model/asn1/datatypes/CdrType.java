/**
 * This class file was automatically generated by jASN1 v1.9.0 (http://www.openmuc.org)
 */

package model.asn1.datatypes;

import org.openmuc.jasn1.ber.types.BerEnum;

import java.math.BigInteger;


public class CdrType extends BerEnum {

    private static final long serialVersionUID = 1L;

    public enum CdrTypes {
        UPDATE_LOCATION(2, "updateLocation"),
        CANCEL_LOCATION(3, "cancelLocation");
        public int value;
        public String name;

        CdrTypes(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    public CdrType() {
    }

    public CdrType(byte[] code) {
        super(code);
    }

    public CdrType(BigInteger value) {
        super(value);
    }

    public CdrType(long value) {
        super(value);
    }

}