import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PalindromeController {

    @PostMapping("/api/v1/checkpalindrome")
    public ResponseEntity<Result> checkPalindrome(@RequestBody RequestBody request) {
        String motAverifier = request.getMotAverifier();
        boolean isPalindrome = checkIfPalindrome(motAverifier);

        Result result = new Result(isPalindrome);
        HttpStatus status = isPalindrome ? HttpStatus.ACCEPTED : HttpStatus.OK;

        return new ResponseEntity<>(result, status);
    }

    private boolean checkIfPalindrome(String word) {
        StringBuilder reversed = new StringBuilder(word).reverse();
        return word.equalsIgnoreCase(reversed.toString());
    }
}

class RequestBody {
    private String motAverifier;

    public String getMotAverifier() {
        return motAverifier;
    }

    public void setMotAverifier(String motAverifier) {
        this.motAverifier = motAverifier;
    }
}

class Result {
    private boolean isPalindrome;

    public Result(boolean isPalindrome) {
        this.isPalindrome = isPalindrome;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean palindrome) {
        isPalindrome = palindrome;
    }
}
