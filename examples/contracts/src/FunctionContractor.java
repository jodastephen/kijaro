import java.io.Serializable;

public class FunctionContractor implements Serializable, static Function<String, Number> {

	public static String invoke(Number param) {
		return param.toString();
	}
	
}
	
