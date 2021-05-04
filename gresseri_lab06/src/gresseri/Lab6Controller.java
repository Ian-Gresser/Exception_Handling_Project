/* Course: CS1021 - 081
 * Winter
 * Lab 6 - Exceptions
 * Name: Ian Gresser
 * Created: 1-23-20
 * Last modified: 1-29-20
 */
package gresseri;

import edu.msoe.se1021.Lab6.WebsiteTester;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Controller class of the lab6.fxml. Runs gui that takes a url and displays various information if
 * it works, uses exceptions to display error alerts if it doesn't work
 */
public class Lab6Controller {
    /**
     * Button that runs the analyze method
     */
    @FXML
    public Button analyze;
    /**
     * Text field that displays the download time
     */
    @FXML
    public TextField downloadTime;
    /**
     * Text field that the user types the url in
     */
    @FXML
    public TextField url;
    /**
     * Text field that displays the download size
     */
    @FXML
    public TextField size;
    /**
     * Text field that displays the host of the url
     */
    @FXML
    public TextField host;
    /**
     * Text field the user uses to set the timeout
     */
    @FXML
    public TextField timeout;
    /**
     * Button that sets the timeout
     */
    @FXML
    public Button set;
    /**
     * Label that displays the processed url html
     */
    @FXML
    public Label processedInfo;
    /**
     * Text field that displays the port used
     */
    @FXML
    public TextField port;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private WebsiteTester websiteTester = new WebsiteTester();

    /**
     * On click of the "Analyze" button, run this method. Displays various information on url if it
     * works, uses try catches to catch exceptions and displays alerts according to those exceptions
     */
    public void analyze() {
        boolean urlCheck = true;
        boolean connectionCheck = true;
        boolean textCheck = true;
        try {
            websiteTester.openURL(url.getText());
        } catch (MalformedURLException e) {
            urlCheck = false;
            alert.setContentText("The URL entered into the text box is invalid");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("URL Error");
            alert.show();
        } finally {
            if (urlCheck) {
                try {
                    websiteTester.openConnection();
                } catch (MalformedURLException e) {
                    alert.setContentText("The URL entered into the text box is invalid");
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("URL Error");
                    alert.show();
                } catch (UnknownHostException e) {
                    alert.setContentText("Error: Unable to reach the host " +
                            websiteTester.getHostname());
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Host Error");
                    alert.show();
                } catch (SocketTimeoutException e) {
                    TextInputDialog alert = new TextInputDialog();
                    alert.setContentText("The URL entered into the text box is invalid");
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Wait Longer?");
                    alert.show();
                    alert.getEditor().setText("100");
                    alert.getDialogPane().lookupButton(ButtonType.OK).setOnMousePressed(
                            (MouseEvent event) -> websiteTester.setTimeout((alert.getEditor().getText())));
                } catch (IOException e) {
                    connectionCheck = false;
                } finally {
                    if (connectionCheck) {
                        try {
                            websiteTester.downloadText();
                        } catch (UnknownHostException e) {
                            alert.setContentText("Error: Unable to reach the host " +
                                    websiteTester.getHostname());
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("Host Error");
                            alert.show();
                        } catch (ConnectException e) {
                            alert.setContentText("Error: Unable to connect to given URL");
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("Connection Error");
                            alert.show();
                        } catch (FileNotFoundException e) {
                            alert.setContentText("Error: File not found on the server,\n" +
                                    websiteTester.getHostname());
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("File Error");
                            alert.show();
                        } catch (IOException e) {
                            textCheck = false;
                        } finally {
                            if (textCheck) {
                                size.setText(Integer.toString(websiteTester.getSize()));
                                downloadTime.setText(Long.toString(
                                        websiteTester.getDownloadTime()));
                                port.setText(Integer.toString(websiteTester.getPort()));
                                host.setText(websiteTester.getHostname());
                                processedInfo.setText(websiteTester.getContent());
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * On the "set" button click in the gui, run this method. If the timeout time is valid, set
     * that as the timeout time. If not, display a error alert
     */
    public void setTimeout() {
        try {
            websiteTester.setTimeout(timeout.getText());
        } catch (NumberFormatException e) {
            alert.setContentText("Timeout must be greater than or equal to 0.");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Invalid Timeout Error");
            alert.show();
        }
    }
}
