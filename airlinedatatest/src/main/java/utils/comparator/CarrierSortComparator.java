package utils.comparator;

import org.apache.hadoop.io.WritableComparator;

import utils.keynval.CarrierKey;

public class CarrierSortComparator extends WritableComparator {
    public CarrierSortComparator() {

        super(CarrierKey.class, true);

    }

}