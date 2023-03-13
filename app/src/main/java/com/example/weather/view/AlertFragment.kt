package com.example.weather.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.work.*
import com.example.weather.R
import com.example.weather.alert.AlertModel
import com.example.weather.alert.AlertWorker
import com.example.weather.databinding.FragmentAlertBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlertFragment : Fragment() {
    var yearStart = 0
    var monthStart = 0
    var dayStart = 0
    var hourStart = 0
    var minutsStart = 0

    //----------------------
    var yearEnd = 0
    var monthEnd = 0
    var dayEnd = 0
    var hourEnd = 0
    var minutsEnd = 0

    var typeAlert = "Notification"

    lateinit var timeStart: EditText
    lateinit var timeEnd: EditText

    lateinit var dateStart: EditText
    lateinit var dateEnd: EditText
    lateinit var binding: FragmentAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_alert, container, false)
        binding = FragmentAlertBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddAlert.setOnClickListener() {
            displayAlertInfo()
        }
        val pm = view!!.context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!Settings.canDrawOverlays(view!!.context)) {
            askForDrawOverlaysPermission()
        }
    }

    fun displayAlertInfo() {
        val dialogInfo = Dialog(requireContext())
        dialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogInfo.setCancelable(false)
        dialogInfo.setContentView(R.layout.add_alert_dialog_card)
        dialogInfo.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var cardviewFrom: CardView = dialogInfo.findViewById(R.id.cardview_alert_from)
         timeStart  = dialogInfo.findViewById(R.id.edt_start_time)
         dateStart = dialogInfo.findViewById(R.id.edt_start_date)

        var cardviewTo: CardView = dialogInfo.findViewById(R.id.cardview_alert_to)
         timeEnd = dialogInfo.findViewById(R.id.edt_end_time)
         dateEnd = dialogInfo.findViewById(R.id.edt_end_date)
        var radioGroup: RadioGroup = dialogInfo.findViewById(R.id.radioGroupAlert)
        var radioBtnAlerm: AppCompatRadioButton =
            dialogInfo.findViewById(R.id.appCompatRadioBtn_alarm)
        var radioBtnNotification: AppCompatRadioButton =
            dialogInfo.findViewById(R.id.appCompatRadioBtn_notification)
        var buttonAdd: Button = dialogInfo.findViewById(R.id.btn_add_alert)
        var buttonCancle: Button = dialogInfo.findViewById(R.id.btn_cancle_alert)


        radioGroup.check(radioBtnNotification.id)

        cardviewFrom.setOnClickListener() {
            createDatePickerDialog("From")
        }
        cardviewTo.setOnClickListener() {
            createDatePickerDialog("TO")

        }
        radioBtnAlerm.setOnClickListener() {
            typeAlert = "Alerm"

        }
        radioBtnNotification.setOnClickListener() {
            typeAlert = "Notification"

        }
        buttonAdd.setOnClickListener() {
            var alertModelStart =
                AlertModel(yearStart, monthStart, dayStart, hourStart, minutsStart)
            var alertModelEnd = AlertModel(yearEnd, monthEnd, dayEnd, hourEnd, minutsEnd)
            alertNotificationOrAlarm(alertModelStart, alertModelEnd, typeAlert)
            Toast.makeText(requireContext(), "Add", Toast.LENGTH_SHORT).show()

            dialogInfo.dismiss()

        }
        buttonCancle.setOnClickListener(){
            dialogInfo.dismiss()
        }
        dialogInfo.show()

    }

    fun createDatePickerDialog(duration: String) {

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val c = Calendar.getInstance()
                c[Calendar.YEAR] = year
                c[Calendar.MONTH] = month
                c[Calendar.DAY_OF_MONTH]=day
                val form = SimpleDateFormat(" d /MM/yyyy")
                var date = form.format(c.time)

                if (duration.equals("From")) {
                    this.yearStart = year
                    this.monthStart = month
                    this.dayStart = day
                    dateStart.setText(date)

                } else {
                    this.yearEnd = year
                    this.monthEnd = month
                    this.dayEnd = day
                    dateEnd.setText(date)

                }


                createTimePickerDialog(duration)

            },
            year,
            month,
            day
        ).show()
    }

    fun createTimePickerDialog(duration: String) {
        val calendar = Calendar.getInstance()
        val hours = calendar[Calendar.HOUR_OF_DAY]
        val mints = calendar[Calendar.MINUTE]
        TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, minuts: Int ->
                val c = Calendar.getInstance()
                c[Calendar.HOUR_OF_DAY] = hour
                c[Calendar.MINUTE] = minuts
                c.timeZone = TimeZone.getDefault()
                val form = SimpleDateFormat("KK:mm a")
                var time = form.format(c.time)

                if (duration.equals("From")) {
                    this.hourStart = hour
                    this.minutsStart = minuts
                    timeStart.setText(time)
                } else {
                    this.hourEnd = hour
                    this.minutsEnd = minuts
                    timeEnd.setText(time)
                }

            },
            hours,
            mints,
            DateFormat.is24HourFormat(requireContext())
        ).show()

    }

    fun alertNotificationOrAlarm(start: AlertModel, end: AlertModel, type: String) {
        var startDate = Calendar.getInstance()
        startDate.set(start.year, start.month, start.day, start.hour, start.minute)

        var endDate = Calendar.getInstance()
        endDate.set(end.year, end.month, end.day, end.hour, end.minute)

        var diff = (endDate.timeInMillis / 1000L) - (startDate.timeInMillis / 1000L)
        val inputData = Data.Builder()
            .putString("title", "Weather")
            .putString("content", "current weather statue")
            .putString("typeAlert", type)
            .build()

        /*  val fireAlertConstraints = Constraints.Builder()
              .setRequiresBatteryNotLow(true)
              .setRequiredNetworkType(NetworkType.CONNECTED)
              .setRequiresCharging(true)
              .setRequiresStorageNotLow(true)
              .setRequiresDeviceIdle(true)
              .build()*/

        val periodicWorkRequest = OneTimeWorkRequestBuilder<AlertWorker>()
            .setInitialDelay(diff, TimeUnit.SECONDS)
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(requireContext().applicationContext).enqueue(periodicWorkRequest)

        //   WorkManager.getInstance().cancelWorkById(periodicWorkRequest.id)

    }

    //------------------------------------------------

    private fun askForDrawOverlaysPermission() {
        if (!Settings.canDrawOverlays(view!!.context)) {
            if ("xiaomi" == Build.MANUFACTURER.lowercase(Locale.ROOT)) {
                val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                intent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity"
                )
                intent.putExtra("extra_pkgname", view!!.context.packageName)
                AlertDialog.Builder(view!!.context)
                    .setTitle("draw_overlays")
                    .setMessage("draw_overlays_description")
                    .setPositiveButton("go_to_settings") { dialog, which ->
                        startActivity(
                            intent
                        )
                    }
                    .show()
            } else {
                AlertDialog.Builder(view!!.context)
                    .setTitle("warning")
                    .setMessage("error_msg_permission_required")
                    .setPositiveButton("ok") { _, _ ->
                        val permissionIntent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + view!!.context.packageName)
                        )
                        runtimePermissionResultLauncher.launch(permissionIntent)
                    }
                    .show()
            }
        }

    }

    private val runtimePermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

}