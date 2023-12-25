import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tester {

    public static void main(String[] args) throws NoSuchFieldException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        var obj = new Object(){

            private final String userId, password;

            {
                userId = "hello world";
                password = "password";
            }



            public String toString(){
                return userId + " " + password;
            }


        };

        System.out.println(
                mapper.writeValueAsString(obj)
        );
    }


}
