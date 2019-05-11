package com.ed.application;

import javax.swing.JFrame;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ed.config.GeneralConfig;
import com.ed.views.AddNewTaskFrame;
import com.ed.views.MainAppFrame;

public class AgendaApplication {

	public static void main(String[] args) {
		
		MainAppFrame mainAppFrame = new MainAppFrame("AGENDA - Your daily task Manager");

	}
}
