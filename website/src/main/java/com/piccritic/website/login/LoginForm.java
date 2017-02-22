package com.piccritic.website.login;

import com.piccritic.website.PicCritic;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * This object implements a form to allow users to enter login information
 * 
 * @author Damien Robichaud <br>
 *         Francis Bosse
 */
public class LoginForm extends FormLayout {

	private TextField handle = new TextField("Handle");
	private PasswordField password = new PasswordField("Password");
	private Button login = new Button("login", this::loginAction);
	private Button signUp = new Button("Sign Up");

	public LoginForm() {
		handle.setRequired(true);
		handle.setRequiredError("Must enter handle");
		password.setRequired(true);
		password.setRequiredError("Must enter password");
		HorizontalLayout actions = new HorizontalLayout(login, signUp);
		addComponents(handle, password, actions);
	}

	public void loginAction(Button.ClickEvent event) {
		try {
			handle.validate();
			password.validate();
		} catch (EmptyValueException e) {
			Notification.show(e.getLocalizedMessage(), Type.WARNING_MESSAGE);
		}
		String userHandle = handle.getValue();
		String userPassword = password.getValue();
		switch (getUI().loginService.loginUser(userHandle, userPassword)) {
		case INVALID_INFO:
			Notification.show("Invalid Credentials", Type.WARNING_MESSAGE);
			break;
		case LOGGED_IN:
			AuthService.createUserSession(userHandle);
			Notification.show("Logged in",Type.TRAY_NOTIFICATION);
			break;
		default:
			Notification.show("Unknown Error", Type.WARNING_MESSAGE);
		}
	}
	
	@Override
	public PicCritic getUI() {
		return (PicCritic) super.getUI();
	}
}