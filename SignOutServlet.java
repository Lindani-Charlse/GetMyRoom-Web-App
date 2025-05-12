///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package za.ac.tut.web;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import javax.ejb.EJB;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import za.ac.tut.bl.StudentProfileFacadeLocal;
//import za.ac.tut.entities.StudentProfile;
//
///**
// *
// * @author linda
// */
//public class SignOutServlet extends HttpServlet {
//    @EJB
//    private StudentProfileFacadeLocal fcl;
//    
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException 
//    {
//        String studentNumber = (String)request.getParameter("studentNumber");
//        
//        List<StudentProfile> list = fcl.findAll();
//        StudentProfile theProfile = new StudentProfile();
//        
//        for(StudentProfile profile : list)
//        {
//            if(profile.getStudentNo().equals(studentNumber)){
//                theProfile = profile;
//            }
//        }
//        fcl.remove(theProfile);
//        
//        RequestDispatcher dis = request.getRequestDispatcher("main.html");
//        dis.forward(request, response);
//    }
//}
