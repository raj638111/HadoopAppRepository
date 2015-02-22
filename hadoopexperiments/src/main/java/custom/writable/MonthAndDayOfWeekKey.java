package custom.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;


public class MonthAndDayOfWeekKey implements WritableComparable<MonthAndDayOfWeekKey>{
    public int monthSort = 1;
    public int dowSort = -1;
    
    public IntWritable month=new IntWritable();
    public IntWritable dayOfWeek = new IntWritable();
 
    public MonthAndDayOfWeekKey(){ 
    }

    public void write(DataOutput out) throws IOException {
        this.month.write(out);
        this.dayOfWeek.write(out);
    }
    
    public void readFields(DataInput in) throws IOException {
        this.month.readFields(in);
        this.dayOfWeek.readFields(in);
    }
    
    public int compareTo(MonthAndDayOfWeekKey second) {
    	System.out.println("WritableComparable(Key) : compareTo() : Comparing months : " 
    				+ this.month.get() + ", " + second.month.get());
        if(this.month.get()==second.month.get()){
            return -1 * this.dayOfWeek.compareTo(second.dayOfWeek);
        }
        else{
            return 1 * this.month.compareTo(second.month);
        }
    }
    
    @Override
    public boolean equals(Object o) {
    	System.out.println("WritableComparable(Key) : equals() : Inside equals() method");
        if (!(o instanceof MonthAndDayOfWeekKey)) {
          return false;
        }
        MonthAndDayOfWeekKey other = (MonthAndDayOfWeekKey)o;
        return this.month.get() == other.month.get() && this.dayOfWeek.get() == other.dayOfWeek.get();         
      }
    
    @Override
    public int hashCode() {
    	System.out.println("WritableComparable(Key) : hashCode() : Inside hashCode() method");
        return (this.month.get()-1);
    }
}

