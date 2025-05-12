/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.tut.model;

/**
 *
 * @author linda
 */
public class PasswordValidator implements PasswordValidatorInterface {

    @Override
    public boolean isPasswordValid(String pswd) {
        boolean isValid = false;
        
        if(isThePasswordValid(pswd)) {
            isValid = true;
        }
        
        return isValid;
    }
    
    private boolean isThePasswordValid(String pswd) {
        char charAtIndex;
        boolean isValid = false;
        int cntLetters = 0, cntDigits = 0, cntSpecialChars = 0;
        
        if(pswd.length() >= MINIMUM_LENGTH) {
            for(int i = 0; i < pswd.length(); i++) {
                charAtIndex = pswd.charAt(i);
                
                if(Character.isLetter(charAtIndex)) {
                    cntLetters++;
                } else if(Character.isDigit(charAtIndex)) {
                    cntDigits++;
                } else {
                    cntSpecialChars++;
                }
            }
        }
        
        if(cntLetters >= THRESHOLD && cntDigits >= THRESHOLD && cntSpecialChars >= THRESHOLD)
        {
            isValid = true;
        }
        
        return isValid;
    }

    @Override
    public boolean isPasswordsTheSame(String pswd, String cnfm_pswd) {
        boolean isTheSame = false;
        
        if(pswd.equals(cnfm_pswd)) {
            isTheSame = true;
        }
        
        return isTheSame;
    }
    
}
