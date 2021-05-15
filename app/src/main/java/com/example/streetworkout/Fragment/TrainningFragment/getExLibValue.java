package com.example.streetworkout.Fragment.TrainningFragment;

public class getExLibValue {
    String bodyPart;
    String level;

    public getExLibValue() {
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String bodyPart(int input) {
        switch (input) {
            case 0: {
                return "BACK";

            }
            case 1: {
                return "BICEPT";

            }
            case 2: {
                return "LEGS";

            }
            case 3: {
                return "CHEST";

            }
            case 4: {
                return "TRICEPT";

            }
            case 5: {
                return "ABS";

            }
            case 6: {
                return "SHOULDER";

            }
            case 7: {
                return "WHOLE BODY";

            }
        }
        return bodyPart.toUpperCase();
    }



    public String Level(int input) {
        switch (input) {
            case 0: {
                return "BEGINNER";

            }
            case 1: {
                return "INTERMEDIATE";

            }
            case 2: {
                return "ADVANCE";

            }
        }
        return level.toUpperCase();

    }
}
