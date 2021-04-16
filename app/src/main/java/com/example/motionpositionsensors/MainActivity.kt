package com.example.motionpositionsensors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null

    // sensors
    private var mSensorMagneticField: Sensor? = null
    private var mSensorAccelerometer: Sensor? = null
    private var mSensorGyroscope: Sensor? = null
    private var mSensorRotationVector: Sensor? = null
    private var mSensorGravity: Sensor? = null
    private var mSensorLinearAcceleration: Sensor? = null
    private var mSensorGeomagneticRotationVector: Sensor? = null
    private var mSensorProximity: Sensor? = null

    // TextViews to display current sensor values
    private var tvMagX: TextView? = null
    private var tvMagY: TextView? = null
    private var tvMagZ: TextView? = null

    private var tvAccX: TextView? = null
    private var tvAccY: TextView? = null
    private var tvAccZ: TextView? = null

    private var tvGyroX: TextView? = null
    private var tvGyroY: TextView? = null
    private var tvGyroZ: TextView? = null

    private var tvRotvX: TextView? = null
    private var tvRotvY: TextView? = null
    private var tvRotvZ: TextView? = null
    private var tvRotvS: TextView? = null

    private var tvGravX: TextView? = null
    private var tvGravY: TextView? = null
    private var tvGravZ: TextView? = null

    private var tvLineX: TextView? = null
    private var tvLineY: TextView? = null
    private var tvLineZ: TextView? = null

    private var tvGrtX: TextView? = null
    private var tvGrtY: TextView? = null
    private var tvGrtZ: TextView? = null

    private var tvProx: TextView? = null

    private var tvAzimuth: TextView? = null
    private var tvPitch: TextView? = null
    private var tvRoll: TextView? = null

    // Sensor's values
    private var mag  = FloatArray(3)
    private var acc  = FloatArray(3)
    private var gyro = FloatArray(3)
    private var rotv = FloatArray(4)
    private var grav = FloatArray(3)
    private var line = FloatArray(3)
    private var grv  = FloatArray(3)
    private var prox = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Identify the sensors that are on a device
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Assign the textViews
        tvMagX = findViewById<View>(R.id.label_magX) as TextView
        tvMagY = findViewById<View>(R.id.label_magY) as TextView
        tvMagZ = findViewById<View>(R.id.label_magZ) as TextView

        tvAccX = findViewById<View>(R.id.label_accX) as TextView
        tvAccY = findViewById<View>(R.id.label_accY) as TextView
        tvAccZ = findViewById<View>(R.id.label_accZ) as TextView

        tvGyroX = findViewById<View>(R.id.label_gyroX) as TextView
        tvGyroY = findViewById<View>(R.id.label_gyroY) as TextView
        tvGyroZ = findViewById<View>(R.id.label_gyroZ) as TextView

        tvRotvX = findViewById<View>(R.id.label_rotvX) as TextView
        tvRotvY = findViewById<View>(R.id.label_rotvY) as TextView
        tvRotvZ = findViewById<View>(R.id.label_rotvZ) as TextView
        tvRotvS = findViewById<View>(R.id.label_rotvS) as TextView

        tvGravX = findViewById<View>(R.id.label_gravX) as TextView
        tvGravY = findViewById<View>(R.id.label_gravY) as TextView
        tvGravZ = findViewById<View>(R.id.label_gravZ) as TextView

        tvLineX = findViewById<View>(R.id.label_lineX) as TextView
        tvLineY = findViewById<View>(R.id.label_lineY) as TextView
        tvLineZ = findViewById<View>(R.id.label_lineZ) as TextView

        tvGrtX = findViewById<View>(R.id.label_grtX) as TextView
        tvGrtY = findViewById<View>(R.id.label_grtY) as TextView
        tvGrtZ = findViewById<View>(R.id.label_grtZ) as TextView

        tvProx = findViewById<View>(R.id.label_prox) as TextView

        tvAzimuth = findViewById<View>(R.id.label_azimuth) as TextView
        tvPitch = findViewById<View>(R.id.label_pitch) as TextView
        tvRoll = findViewById<View>(R.id.label_roll) as TextView

        // sensors connection
        mSensorMagneticField = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        mSensorAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorGyroscope = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        mSensorRotationVector = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        mSensorGravity = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GRAVITY)
        mSensorLinearAcceleration = mSensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mSensorGeomagneticRotationVector = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        mSensorProximity = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Check if all sensors are available
        val sensor_error = resources.getString(R.string.error_no_sensor)
        if (mSensorMagneticField == null) {
            tvMagX!!.text = sensor_error
            tvMagY!!.text = sensor_error
            tvMagZ!!.text = sensor_error
        }
        if (mSensorAccelerometer == null) {
            tvAccX!!.text = sensor_error
            tvAccY!!.text = sensor_error
            tvAccZ!!.text = sensor_error
        }
        if (mSensorGyroscope == null) {
            tvGyroX!!.text = sensor_error
            tvGyroY!!.text = sensor_error
            tvGyroZ!!.text = sensor_error
        }
        if (mSensorRotationVector == null) {
            tvRotvX!!.text = sensor_error
            tvRotvY!!.text = sensor_error
            tvRotvZ!!.text = sensor_error
            tvRotvS!!.text = sensor_error
        }
        if (mSensorGravity == null) {
            tvGravX!!.text = sensor_error
            tvGravY!!.text = sensor_error
            tvGravZ!!.text = sensor_error
        }
        if (mSensorLinearAcceleration == null) {
            tvLineX!!.text = sensor_error
            tvLineY!!.text = sensor_error
            tvLineZ!!.text = sensor_error
        }
        if (mSensorGeomagneticRotationVector == null) {
            tvGrtX!!.text = sensor_error
            tvGrtY!!.text = sensor_error
            tvGrtZ!!.text = sensor_error
        }
        if (mSensorProximity == null) {
            tvProx!!.text = sensor_error
        }
        if((mSensorMagneticField == null) && (mSensorAccelerometer == null)){
            tvAzimuth!!.text = sensor_error
            tvPitch!!.text = sensor_error
            tvRoll!!.text = sensor_error
        }
    }

    override fun onStart() {
        super.onStart()
        // Start listening the sensors that are available
        if (mSensorMagneticField != null) { mSensorManager!!.registerListener(this, mSensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorAccelerometer != null) { mSensorManager!!.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorGyroscope != null) { mSensorManager!!.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorRotationVector != null) { mSensorManager!!.registerListener(this, mSensorRotationVector, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorGravity != null) { mSensorManager!!.registerListener(this, mSensorGravity, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorLinearAcceleration != null) { mSensorManager!!.registerListener(this, mSensorLinearAcceleration, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorGeomagneticRotationVector != null) { mSensorManager!!.registerListener(this, mSensorGeomagneticRotationVector, SensorManager.SENSOR_DELAY_NORMAL) }
        if (mSensorProximity != null) { mSensorManager!!.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onStop() {
        super.onStop()
        // Stop listening the sensors
        mSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Get sensors data when values changed
        val sensorType = event.sensor.type
        when (sensorType) {
            Sensor.TYPE_MAGNETIC_FIELD -> {
                mag = event.values
                tvMagX!!.text = resources.getString(R.string.label_magneticFieldX, mag[0])
                tvMagY!!.text = resources.getString(R.string.label_magneticFieldY, mag[1])
                tvMagZ!!.text = resources.getString(R.string.label_magneticFieldZ, mag[2])
                computeOrientation()
            }
            Sensor.TYPE_ACCELEROMETER -> {
                acc = event.values
                tvAccX!!.text = resources.getString(R.string.label_accelerometerX, acc[0])
                tvAccY!!.text = resources.getString(R.string.label_accelerometerY, acc[1])
                tvAccZ!!.text = resources.getString(R.string.label_accelerometerZ, acc[2])
                computeOrientation()
            }
            Sensor.TYPE_GYROSCOPE -> {
                gyro = event.values
                tvGyroX!!.text = resources.getString(R.string.label_gyroscopeX, gyro[0])
                tvGyroY!!.text = resources.getString(R.string.label_gyroscopeY, gyro[1])
                tvGyroZ!!.text = resources.getString(R.string.label_gyroscopeZ, gyro[2])
            }
            Sensor.TYPE_ROTATION_VECTOR -> {
                rotv = event.values
                tvRotvX!!.text = resources.getString(R.string.label_rotvX, rotv[0])
                tvRotvY!!.text = resources.getString(R.string.label_rotvY, rotv[1])
                tvRotvZ!!.text = resources.getString(R.string.label_rotvZ, rotv[2])
                tvRotvS!!.text = resources.getString(R.string.label_rotvS, rotv[3])
            }
            Sensor.TYPE_GRAVITY -> {
                grav = event.values
                tvGravX!!.text = resources.getString(R.string.label_gravX, grav[0])
                tvGravY!!.text = resources.getString(R.string.label_gravY, grav[1])
                tvGravZ!!.text = resources.getString(R.string.label_gravZ, grav[2])
            }
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                line = event.values
                tvLineX!!.text = resources.getString(R.string.label_lineX, line[0])
                tvLineY!!.text = resources.getString(R.string.label_lineY, line[1])
                tvLineZ!!.text = resources.getString(R.string.label_lineZ, line[2])
            }
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                grv = event.values
                tvGrtX!!.text = resources.getString(R.string.label_gravX, grv[0])
                tvGrtY!!.text = resources.getString(R.string.label_gravY, grv[1])
                tvGrtZ!!.text = resources.getString(R.string.label_gravZ, grv[2])
            }
            Sensor.TYPE_PROXIMITY -> {
                prox = event.values[0].toDouble()
                tvProx!!.text = resources.getString(R.string.label_prox, prox)
            }
            else -> { }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    fun computeOrientation() {
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrix(rotationMatrix, null, acc, mag)

        val orientationAngles = FloatArray(3)
        var radian = SensorManager.getOrientation(rotationMatrix, orientationAngles)

        // Convert angles from radians to degree
        val angles = FloatArray(3)
        angles[0] = (radian[0].toDouble() * 180 / 3.14).toFloat()
        angles[1] = (radian[1].toDouble() * 180 / 3.14).toFloat()
        angles[2] = (radian[2].toDouble() * 180 / 3.14).toFloat()

        tvAzimuth!!.text = resources.getString(R.string.label_azimuth, angles[0])
        tvPitch!!.text = resources.getString(R.string.label_pitch, angles[1])
        tvRoll!!.text = resources.getString(R.string.label_roll, angles[2])
    }
}