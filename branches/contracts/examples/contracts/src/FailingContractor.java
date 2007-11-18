public class FailingContractor implements static Function<String, Number> {
	
	public static String invoke(Long param) {
		return param.toString();
	}
	
}

