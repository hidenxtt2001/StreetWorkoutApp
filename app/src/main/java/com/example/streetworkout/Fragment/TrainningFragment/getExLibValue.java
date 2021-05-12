package com.example.streetworkout.Fragment.TrainningFragment;

public class getExLibValue {
    String bodyPart;
    String level;


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
                return "Back";

            }
            case 1: {
                return "Bicept";

            }
            case 2: {
                return "Legs";

            }
            case 3: {
                return "Chest";

            }
            case 4: {
                return "Tricept";

            }
            case 5: {
                return "Abs";

            }
            case 6: {
                return "Shoulder";

            }
            case 7: {
                return "Whole Body";

            }
        }
        return bodyPart;
    }



    public String Level(int input) {
        switch (input) {
            case 0: {
                return "Beginner";

            }
            case 1: {
                return "Intermediate";

            }
            case 2: {
                return "Advanced";

            }
        }
        return level;

    }
}
