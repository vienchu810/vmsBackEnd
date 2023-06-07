package com.example.vms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public static ResponseEntity<Object> id(String id) {
        return ResponseEntity.ok(new Id(id));
    }

    public static ResponseEntity<Object> data(Object data) {
        return ResponseEntity.ok(new Data(data));
    }

    public static ResponseEntity<Object> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new Error(message));
    }

    public static ResponseEntity<Object> error(HttpStatus status, String message, String code, String... prms) {
        return ResponseEntity.status(status).body(new Error(message,code, prms));
    }

    //For 3th party API call
    public static ResponseEntity<Object> dataJson(Object data) {
        JSONObject root = new JSONObject();
        root.put("data", data);
        root.put("status", "success");
        return ResponseEntity.ok(root.toString());
    }

    //For 3th party API call
    public static ResponseEntity<Object> dataJsonArray(JSONArray data) {
        JSONObject root = new JSONObject();
        root.put("data", data);
        root.put("status", "success");
        return ResponseEntity.ok(root.toString());
    }

    @Getter
    public static class Base {
        protected String apiVersion = "0.0.1";
        protected String status;
        protected  int msgCode;
    }

    @Getter
    private static class Id extends Base {
        private String id;

        private Id(String id) {
            {
                this.status = "success";
                this.id = id;
                this.msgCode = 1;
            }
        }
    }

    @Getter
    private static class Data extends Base {
        private Object data;

        private Data(Object data) {
            {
                this.status = "success";
                this.data = data;

            }
        }
    }

    @Getter
    private static class Error extends Base {
        private ErrorInfo error;
        private Error( String message) {
            {
                this.status = "error";
                this.error = new ErrorInfo(message);
                this.msgCode = 0;
            }
        }

        private Error( String message, String code, String... prms) {
            {
                this.status = "error";
                this.error = new ErrorInfo(message, code, prms);
                this.msgCode = 0;
            }
        }
    }

    @Getter
    @AllArgsConstructor
    private static class ErrorInfo {
        private String message;
        private String code;
        private String[] prms;

        public ErrorInfo(String message) {
            this.message = message;
        }
    }
}
