package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

// Motor and arm tester

@TeleOp(name="Motor Tester", group="Dev")
public class MotorTesting extends LinearOpMode {

    // Declare members
    private FourWheelRobot robot;

    private DcMotor armLeft;
    private DcMotor armRight;

    private Servo clawLeft;
    private Servo clawRight;

    @Override
    public void runOpMode() {
        robot = new FourWheelRobot(hardwareMap);
        robot.reset();

        armLeft = RobotUtil.getDcMotor(
            hardwareMap,
            "armLeft",
            DcMotorSimple.Direction.REVERSE,
            DcMotor.ZeroPowerBehavior.FLOAT,
            DcMotor.RunMode.RUN_USING_ENCODER
        );
        armRight = RobotUtil.getDcMotor(
            hardwareMap,
            "armRight",
            DcMotorSimple.Direction.FORWARD,
            DcMotor.ZeroPowerBehavior.FLOAT,
            DcMotor.RunMode.RUN_USING_ENCODER
        );

        clawLeft = RobotUtil.getServo(
                hardwareMap,
                "clawLeft",
                Servo.Direction.REVERSE
        );
        clawRight = RobotUtil.getServo(
                hardwareMap,
                "clawRight",
                Servo.Direction.FORWARD
        );

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                clawLeft.setPosition(0.0);
                clawRight.setPosition(0.0);
            }
            else if (gamepad1.b) {
                clawLeft.setPosition(1.0);
                clawRight.setPosition(1.0);
            }

            telemetry.addData("arm left", armLeft.getCurrentPosition());
            telemetry.addData("arm right", armRight.getCurrentPosition());
            telemetry.addData("left front wheel", robot.leftFront.getCurrentPosition());
            telemetry.addData("left trigger", gamepad1.left_trigger);

            telemetry.addData("left claw", clawLeft.getPosition());
            telemetry.addData("right claw", clawRight.getPosition());
            telemetry.update();
        }
    }

    private double armPosition;
    private final double maxArmPos = 100.0;
    private final double minArmPos = 0.0;

    private void initArm() {
        setArmPosition(minArmPos);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLeft.setPower(0.5);
        armRight.setPower(0.5);
    }
    private void setArmPosition(double ticks) {
        // Clamp arm position between min and max.
        // ticks = Math.max(minArmPos, Math.min(ticks, maxArmPos));

        int output = (int) Math.round(ticks);
        armLeft.setTargetPosition(output);
        armRight.setTargetPosition(output);
        armPosition = ticks;
    }
    private void shiftArmPosition(double dTicks) {
        setArmPosition(armPosition + dTicks);
    }



}
