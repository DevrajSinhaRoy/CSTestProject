package utilities.POJO;
import lombok.*;
import utilities.ApiRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidateBatchApiRequest extends ApiRequest {
    List<ValidateApiRequest> list = new ArrayList<>();

   public ValidateBatchApiRequest setlist(ValidateApiRequest validateApiRequest) {
        list.add(validateApiRequest);
        return this;
   }

   public ValidateBatchApiRequest emptyList() {
       list.clear();
       return this;
   }
}
