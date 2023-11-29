package iuh.vn.week07.DTOs.Errors;

import java.util.List;
import java.util.ArrayList;

public class ErrorModel {
    private List<String> errors = new ArrayList<String>();

    public boolean IsEmpty() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void Add(String error) {
        errors.add(error);
    }
}
