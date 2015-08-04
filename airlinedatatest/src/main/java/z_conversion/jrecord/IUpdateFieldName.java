package z_conversion.jrecord;

public interface IUpdateFieldName {

	/**
	 * Convert Cobol/RecordEditor Name to standard name
	 *
	 * @param name current field name
	 *
	 * @return new name
	 */
	public abstract String updateName(String name);

}