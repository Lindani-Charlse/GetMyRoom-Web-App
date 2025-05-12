/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package za.ac.tut.model;

/**
 *
 * @author linda
 */
public interface PasswordValidatorInterface {
    public int THRESHOLD = 2;
    public int MINIMUM_LENGTH = 8;
    public boolean isPasswordValid(String pswd);
     public boolean isPasswordsTheSame(String pswd, String cnfm_pswd);
}
