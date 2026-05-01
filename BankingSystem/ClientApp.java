public class ClientApp {
    public static void main(String[] args) {

        Client client = new Client();
        client.connect("localhost", 1234);

        // LOGIN request
        Message loginMsg = new Message(
            userType.CUSTOMER,
            userStatus.UNDEFINED,
            commandType.LOGIN,
            commandStatus.UNDEFINED,
            null,
            null,
            "1001",     // userID
            "1234"      // PIN
        );

        Message response = client.sendMessage(loginMsg);

        System.out.println(response.getText());

        if (response.getCStatus() == commandStatus.SUCCESS) {
            System.out.println("Login success");

            // LOGOUT example
            Message logoutMsg = new Message(
                userType.CUSTOMER,
                userStatus.LOGGED_IN,
                commandType.LOGOUT,
                commandStatus.UNDEFINED,
                response.getCurrCust(),
                null,
                "1001",
                ""
            );

            Message logoutResponse = client.sendMessage(logoutMsg);
            System.out.println(logoutResponse.getText());
        }

        client.close();
    }
}