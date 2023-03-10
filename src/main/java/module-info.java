module com.example.fx_jsonresponse {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.fx_jsonresponse to javafx.fxml;
    exports com.example.fx_jsonresponse;
}