
package za.ac.tut.model;


public class ValidateNumberManager implements ValidateNumberInterface {

    @Override
    public boolean ValidateStudentNumber(String studNumber) throws NumberException {
        boolean isValid = false;
        
        int count = 0;
        
        if(studNumber.length() == 9) {
            
            for(int i = 0; i < studNumber.length(); i++)
            {
                char temp = studNumber.charAt(i);
                
                if(Character.isDigit(temp))
                {
                    count++;
                }
            }
            
            if(count == 9)
            {
                isValid = true;
            }
        } else {
            throw new NumberException(studNumber  + " is not a 9-digit number");
        }
        
        return isValid;
    }

    @Override
    public boolean ValidateContactNumber(String contact) throws NumberException {
        boolean isValidNumber = false;
        
        int count = 0;
        
        if(contact.length() == 10) {
            
            for(int i = 0; i < contact.length(); i++)
            {
                char temp = contact.charAt(i);
                
                if(Character.isDigit(temp))
                {
                    count++;
                }
            }
            
            if(count == 10)
            {
                isValidNumber = true;
            }
        } else {
            throw new NumberException(contact  + " is not a 10-digit number");
        }
        
        return isValidNumber;
    }
}
