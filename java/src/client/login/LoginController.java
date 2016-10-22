package client.login;

import client.base.*;
import client.misc.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Game;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {

		//grab the username and password information
		//consider .trim(); for both username and password here.
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();

		if(canLogin(username, password)){
			//LOGIN HERE
			Game.getInstance().getServer().userLogin(username, password);

			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
		//cannot login... something is wrong
		else {
			messageView.setTitle("Sign In Failed");
			messageView.setMessage("Error signing in.  Try again.");
			messageView.setController(this);
			messageView.showModal();
		}
	}

	@Override
	public void register() {

		String username = getLoginView().getLoginUsername().trim();
		String password = getLoginView().getLoginPassword().trim();
		String passwordConfirm = getLoginView().getLoginPassword().trim();

		if(canRegister(username, password, passwordConfirm)){
			//REGISTER HERE
			Game.getInstance().getServer().userRegister(username, password);

			// If register succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
		//if false
		else {
			messageView.setTitle("Registration Failed");
			messageView.setMessage("Error in registration.  Try again.");
			messageView.setController(this);
			messageView.showModal();

		}

	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

	}

	private boolean canRegister(String username, String password, String passwordConfirm){

		//check if username and password are null
		if(username == null || password == null){
			messageView.setTitle("Registration Error");
			messageView.setMessage("Enter a valid username and password");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}

		//check if the passwords are the same
		if(!password.equals(passwordConfirm)){
			messageView.setTitle("Registration Error");
			messageView.setMessage("Please enter the same password.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		return true;
	}

	private boolean canLogin(String username, String password){

		//maybe check for empty string using string.isEmpty()
		if(username == null || password == null){
			messageView.setTitle("Login Error");
			messageView.setMessage("Please enter a valid username and password.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		else{
			return true;
		}
	}

}

