package org.firstinspires.ftc.teamcode.gadgets

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction as DcDirection
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior as ZPB
import com.qualcomm.robotcore.hardware.DcMotor.RunMode as DcRunMode
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.HardwareMap

import org.firstinspires.ftc.teamcode.common.FourWheelRobot
import org.firstinspires.ftc.teamcode.common.Arm

class GadgetsRobot(hardwareMap: HardwareMap) : FourWheelRobot(hardwareMap) {
    override val leftFront = getWheel("leftFront", DcDirection.FORWARD)
    override val rightFront = getWheel("rightFront", DcDirection.FORWARD)
    override val leftRear = getWheel("leftRear", DcDirection.FORWARD)
    override val rightRear = getWheel("rightRear", DcDirection.FORWARD)

    data class ClawDescriptor(val motor: DcMotor, val open: Double, val close: Double)

    val claws = listOf(
        ClawDescriptor(
            getServo("clawLeft", Servo.Direction.FORWARD),
            0.0, 1.0,
        ),
        ClawDescriptor(
            getServo("clawRight", Servo.Direction.FORWARD),
            0.0, 1.0,
        ),
    )

    val arm = Arm(
        power = 0.2,
        armMotors =
            arrayOf(
                Triple("armLeft", DcDirection.FORWARD, 500),
                Triple("armRight", DcDirection.FORWARD, 500),
            ).map { (name, direction, range) ->
                hardwareMap.getDcMotor(
                    name, direction,
                    ZPB.BRAKE, DcRunMode.RUN_USING_ENCODER,
                ).let {
                    Arm.MotorDescriptor(it, range)
                }
            },
    )

    override fun translate(px: Double, py: Double): FourWheelRobot {
        // Check for NaN
        if (px.isNaN() || py.isNaN())
            throw IllegalArgumentException("You cannot supply NaN into the translate function.")

        // Set motor powers
        wheels.forEach {
            it.power = py
        }
        return this
    }

    fun openClaws() = claws.forEach { it.motor.position = it.open }
    fun closeClaws() = claws.forEach { it.motor.position = it.close }
}
