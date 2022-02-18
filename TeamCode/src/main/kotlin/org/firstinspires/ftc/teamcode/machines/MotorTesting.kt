package org.firstinspires.ftc.teamcode.machines

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

import org.firstinspires.ftc.teamcode.common.LateInitConstProperty
import org.firstinspires.ftc.teamcode.common.leftTriggerPressed
import org.firstinspires.ftc.teamcode.common.rightTriggerPressed

// Motor and arm tester
@TeleOp(name = "Motor Tester", group = "Dev")
class MotorTesting : LinearOpMode() {
    // Declare members
    private var robot: MadMachinesRobot by LateInitConstProperty()

    override fun runOpMode() {
        robot = MadMachinesRobot(hardwareMap)
        robot.reset()

        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // Wait for the game to start (driver presses PLAY)
        waitForStart()
        while (opModeIsActive()) {
//            when {
//                gamepad1.a -> {
//                    robot.clawLeft.position -= 0.001
//                    robot.clawRight.position -= 0.001
//                }
//                gamepad1.b -> {
//                    robot.clawLeft.position += 0.001
//                    robot.clawRight.position += 0.001
//                }
//            }

            for (lol in arrayOf(
                Pair(gamepad2.left_bumper, robot.leftFront),
                Pair(gamepad2.right_bumper, robot.rightFront),
                Pair(gamepad2.leftTriggerPressed, robot.leftRear),
                Pair(gamepad2.rightTriggerPressed, robot.rightRear),
            )) {
                lol.second.power =
                    if (lol.first) 0.5 else 0.0
            }

            robot.clawLeft.position += when {
                gamepad1.leftTriggerPressed -> -0.001
                gamepad1.left_bumper -> 0.001
                else -> 0.0
            }
            robot.clawRight.position += when {
                gamepad1.rightTriggerPressed -> -0.001
                gamepad1.right_bumper -> 0.001
                else -> 0.0
            }
            when {
                gamepad1.dpad_up -> {
                    for (motor in arrayOf(robot.armLeft, robot.armRight))
                        motor.targetPosition += 1
                }
                gamepad1.dpad_down -> {
                    for (motor in arrayOf(robot.armLeft, robot.armRight))
                        motor.targetPosition -= 1
                }
            }
            val thingsToPrint: Array<Pair<String, Any?>> = arrayOf(
                "arm left" to robot.armLeft.currentPosition,
                "arm right" to robot.armRight.currentPosition,
                "left front wheel" to robot.leftFront.currentPosition,
                "left trigger" to gamepad1.left_trigger,
                "left claw" to robot.clawLeft.position,
                "right claw" to robot.clawRight.position,
            )
            for ((caption, content) in thingsToPrint)
                telemetry.addData(caption, content)
            telemetry.update()
        }
    }
}
