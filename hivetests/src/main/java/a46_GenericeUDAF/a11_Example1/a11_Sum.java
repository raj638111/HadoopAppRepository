package a46_GenericeUDAF.a11_Example1;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

import h15_Proj_Aggregation.a11_COLLECT_OBJ.CollectObjEvaluator;

public class a11_Sum extends AbstractGenericUDAFResolver{
	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] tis) 
										throws SemanticException {
		if(tis.length != 1) {
			throw new UDFArgumentTypeException(tis.length - 1, 
								"Exactly one argument is excpected");
		}
		System.out.println("getEvaluator() : TypeName -> " 
									+ tis[0].getTypeName()
									+ ", Category -> "
									+ tis[0].getCategory());
		
		if(tis[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentTypeException(0, 
					"Only Primitive Types are accepted " 
					+ tis[0].getTypeName() + " is passed as parameter 1");
		}
		return new SumEvaluator();
	}	

	public static class SumEvaluator extends GenericUDAFEvaluator {

		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException{
		}	
		
		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void reset(AggregationBuffer agg) throws HiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object terminatePartial(AggregationBuffer agg) throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void merge(AggregationBuffer agg, Object partial) throws HiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
