package com.qrcheckin.qrcheckin.Records;

public record FlashMessage(FlashStatuses status, String message) {



    public static enum FlashStatuses {
        SUCCESS("Success"),WARNING("Warning"),ERROR("Error");

        private final String name;

        private FlashStatuses(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

