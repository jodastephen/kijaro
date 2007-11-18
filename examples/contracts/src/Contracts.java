
public class Contracts {

    public static void main(String[] args) {
        try {
            new Contracts().process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void process() throws Exception {
        System.out.println("Hello Contracts");
        System.out.println(invoke(FunctionContractor.class));
        System.out.println(invoke(new Function<String, Number>() {
        	@Override
        	public String invoke(Number param) {
        		return param.toString();
        	}
        }));
        // The following should not compile
        // System.out.println(invoke(FailingContractor.class));
        // System.out.println(invoke(Function.class));
        // System.out.println(invoke(Method.class));
    }
    
    public String invoke(Function<String, Number> function) {
    	return function.invoke(Long.valueOf(1));
    }
    
}
