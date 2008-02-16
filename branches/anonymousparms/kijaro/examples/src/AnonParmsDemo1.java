
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;

/**
 * Anonymous parameters demo application
 *
 * @author Casper Bang (casper.bang@acm.org)
 */
public class AnonParmsDemo1
{
    interface SomeContract
    {
        /**
         * Partial anonymous signature
         *
         * @param 0 The <B>String</B> to search for
         * @param start The <B>int</B> offset to start from
         */
        public boolean indexOf(String arg, int start);
    }

    /**
     * We can use varargs without necessarily assigning a variable to it
     *
     * @param the command line arguments
     */
    public static void main(String...)
    {
        System.out.println("Static main method invoked with an anonymous args array");

        // You are no longer required to deal with an exception object if you don't need it
        try
        {
            System.out.println("Let's simulate the case of a missing file");
            throw new FileNotFoundException();
        }
        catch(FileNotFoundException)
        {
            System.out.println("I got the message of the missing file, without having to deal with the exception object!");
        }

		// Interface signature need no default parameter names
        try
        {
            new SomeContract()
            {
                // I don't care what the contract says, I won't support this
                public boolean indexOf(String, int)
                {
                    throw new UnsupportedOperationException();
                }
            }.indexOf("CAFEBABE", 0);
        }
        catch(UnsupportedOperationException)
        {
            System.out.println("SomeContract is not supported!");
        }
    }



    /**
     * First required method of MouseMotionListener. This event we are interested in processing.
     *
     * @param evt
     */
    //public void mouseDragged(MouseEvent evt)
    //{
        // Do something with evt...
   // }

    /**
     * Second required method of MouseMotionListener. This event we are NOT interested in processing
     * so we just have to acknowledge the signature, but can avoid declaring a dummy variable name.
     */
    //public void mouseMoved(MouseEvent){}



}
