package driver;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public interface IDriver {
	void startDriver() throws Exception;
	void stopDriver() throws Exception;
	WebDriver get();

	void setOptions() throws MalformedURLException;

}