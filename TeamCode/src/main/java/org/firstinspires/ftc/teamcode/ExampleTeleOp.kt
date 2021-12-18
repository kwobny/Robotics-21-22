package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Example TeleOp", group = "Example Group")
class ExampleTeleOp : LinearOpMode() {
    // Declare members
    private var robot: FourWheelRobot by LateInitConstProperty()

    override fun runOpMode() {
        robot = FourWheelRobot(hardwareMap)
        robot.reset()

        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // Wait for the game to start (driver presses PLAY)
        waitForStart()
        while (opModeIsActive()) {
            // Controller loop
            robot.rotate(when {
                gamepad1.leftTriggerPressed -> -0.5
                gamepad1.rightTriggerPressed -> 0.5
                else -> 0.0
            })
        }
    }
}
