package a46_GenericeUDAF.a11_Example1;

import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaIntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.junit.Test;

import junit.framework.Assert;

public class a15_SumTest {
	
	@Test 	
	public void checkTypeInfo() {
		
		List<Integer> intList = Arrays.asList(1, 2);
		TypeInfo boolTinfo = TypeInfoFactory.booleanTypeInfo;
		TypeInfo doubleTinfo = TypeInfoFactory.doubleTypeInfo;
		
		List<TypeInfo> tiList = 
				Arrays.asList(	
						(TypeInfo)TypeInfoFactory.intTypeInfo,
						(TypeInfo)TypeInfoFactory.floatTypeInfo,
						(TypeInfo)TypeInfoFactory.getListTypeInfo(boolTinfo),
						(TypeInfo)TypeInfoFactory.getListTypeInfo(doubleTinfo));
		
		for(TypeInfo tInfo : tiList) {
			System.out.println("checkTypeInfo() : Type name, Type Category -> "
												+ 	tInfo.getTypeName() + "," 
												+	tInfo.getCategory());
		}
	}
	
	@Test 
	public void getEvaluator() throws SemanticException {
		a11_Sum sum = new a11_Sum();
		sum.getEvaluator(createTypeInfoArr1());
	}
	
	
	@Test 
	public void checkIntObjInspector() {
		//Object Inspector for Java Integer
		JavaIntObjectInspector intOI = 
					PrimitiveObjectInspectorFactory.javaIntObjectInspector;
		
		Integer intNo = 5;
		Object intObj = intNo;
		Integer intResult = (Integer)intOI.getPrimitiveJavaObject(intObj);
		System.out.println("Int value is -> " + intResult);
		Assert.assertEquals(intNo,intResult);
		
		Float floatNo = 1.2f;
		Object floatObj = floatNo;
		Float floatResult = (Float)intOI.getPrimitiveJavaObject(floatObj);
		System.out.println("Float value is -> " + floatResult);
		Assert.assertEquals(floatNo,floatResult);

	}
	
	/*************************************************************/
	
	//Create a TypeInfo array with single element(intTypeInfo)
	public TypeInfo[] createTypeInfoArr1() {
		List<TypeInfo> tiList = 
				Arrays.asList((TypeInfo)TypeInfoFactory.intTypeInfo);
		TypeInfo[] tiArr = (TypeInfo[])tiList.toArray();
		System.out.println("createTypeInfoArr1() : Array length -> "
							+	tiArr.length);
		return tiArr;
	}
}
