package com.ajisaq.ticketingappsanbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajisaq.ticketingappsanbox.Utils.ButtonDelayUtils;
import com.ajisaq.ticketingappsanbox.Utils.HandlerUtils;

import com.amazonaws.amplify.generated.graphql.CreateFuforeTicketsMutation;
import com.amazonaws.amplify.generated.graphql.GetFuforeTicketsQuery;
import com.amazonaws.amplify.generated.graphql.ListFuforeTicketssQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.iposprinter.iposprinterservice.IPosPrinterCallback;
import com.iposprinter.iposprinterservice.IPosPrinterService;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import type.CreateFuforeTicketsInput;


import static com.ajisaq.ticketingappsanbox.MemInfo.bitmapRecycle;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /// device printer parameter

    private final int PRINTER_NORMAL = 0;
    private final int PRINTER_PAPERLESS = 1;
    private final int PRINTER_THP_HIGH_TEMPERATURE = 2;
    private final int PRINTER_MOTOR_HIGH_TEMPERATURE = 3;
    private final int PRINTER_IS_BUSY = 4;
    private final int PRINTER_ERROR_UNKNOWN = 5;
    private int printerStatus = 0;
    private final String  PRINTER_NORMAL_ACTION = "com.iposprinter.iposprinterservice.NORMAL_ACTION";
    private final String  PRINTER_PAPERLESS_ACTION = "com.iposprinter.iposprinterservice.PAPERLESS_ACTION";
    private final String  PRINTER_PAPEREXISTS_ACTION = "com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION";
    private final String  PRINTER_THP_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION";
    private final String  PRINTER_THP_NORMALTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION";
    private final String  PRINTER_MOTOR_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION";
    private final String  PRINTER_BUSY_ACTION = "com.iposprinter.iposprinterservice.BUSY_ACTION";
    private final String  PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION = "com.iposprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION";

    private final int MSG_TEST                               = 1;
    private final int MSG_IS_NORMAL                          = 2;
    private final int MSG_IS_BUSY                            = 3;
    private final int MSG_PAPER_LESS                         = 4;
    private final int MSG_PAPER_EXISTS                       = 5;
    private final int MSG_THP_HIGH_TEMP                      = 6;
    private final int MSG_THP_TEMP_NORMAL                    = 7;
    private final int MSG_MOTOR_HIGH_TEMP                    = 8;
    private final int MSG_MOTOR_HIGH_TEMP_INIT_PRINTER       = 9;
    private final int MSG_CURRENT_TASK_PRINT_COMPLETE     = 10;

    private IPosPrinterService mIPosPrinterService;
    private IPosPrinterCallback callback = null;
    private HandlerUtils.MyHandler handler;
    private static final String TAG                 = "AjisaqReceiptApp";

    private BroadcastReceiver IPosPrinterStatusListener = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action == null)
            {
                Log.d(TAG,"IPosPrinterStatusListener onReceive action = null");
                return;
            }
            Log.d(TAG,"IPosPrinterStatusListener action = "+action);
            if(action.equals(PRINTER_NORMAL_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_IS_NORMAL,0);
            }
            else if (action.equals(PRINTER_PAPERLESS_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_PAPER_LESS,0);
            }
            else if (action.equals(PRINTER_BUSY_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_IS_BUSY,0);
            }
            else if (action.equals(PRINTER_PAPEREXISTS_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_PAPER_EXISTS,0);
            }
            else if (action.equals(PRINTER_THP_HIGHTEMP_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_THP_HIGH_TEMP,0);
            }
            else if (action.equals(PRINTER_THP_NORMALTEMP_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_THP_TEMP_NORMAL,0);
            }
            else if (action.equals(PRINTER_MOTOR_HIGHTEMP_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP,0);
            }
            else if(action.equals(PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_CURRENT_TASK_PRINT_COMPLETE,0);
            }
            else
            {
                handler.sendEmptyMessageDelayed(MSG_TEST,0);
            }
        }
    };
    private HandlerUtils.IHandlerIntent iHandlerIntent = new HandlerUtils.IHandlerIntent()
    {
        @Override
        public void handlerIntent(Message msg)
        {
            switch (msg.what)
            {
                case MSG_TEST:
                    break;
                case MSG_IS_NORMAL:

                    break;
                case MSG_IS_BUSY:
                    Toast.makeText(MainActivity.this, R.string.printer_is_working, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_PAPER_LESS:

                    Toast.makeText(MainActivity.this, R.string.out_of_paper, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_PAPER_EXISTS:
                    Toast.makeText(MainActivity.this, R.string.exists_paper, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_THP_HIGH_TEMP:
                    Toast.makeText(MainActivity.this, R.string.printer_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_MOTOR_HIGH_TEMP:

                    Toast.makeText(MainActivity.this, R.string.motor_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP_INIT_PRINTER, 180000);  //马达高温报警，等待3分钟后复位打印机
                    break;
                case MSG_MOTOR_HIGH_TEMP_INIT_PRINTER:

                    break;
                case MSG_CURRENT_TASK_PRINT_COMPLETE:
                    Toast.makeText(MainActivity.this, R.string.printer_current_task_print_complete, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private ServiceConnection connectService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
            //setButtonEnable(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPosPrinterService = null;
        }
    };









    // end printer param












    SharedPreferences userData, preferences;

    private final String LOCALSTORAGE = "com.ajisaq.storage";
    private final String DAILYSTORAGE = "com.ajisaq.user";
    EditText etCamelNo, etCowNo,etSheepNo, etGoatNo;
    EditText etMaizeNo, etBeansNo, etRiceNo, etMilletNo, etGroundnutNo;
    Button btPrintCamel, btPrintCow,btPrintSheep, btPrintGoat;
    Button btPrintMaize, btPrintBeans, btPrintRice, btPrintMillet, btPrintGroundnut;
    Button btPrintFacilityShop, btPrintFacilityStore,btPrintFacilityRumfa;
    Button btPrintGatePassCar, btPrintGatePassKeke, btPrintGatePassBus, btPrintGatePassHilux,
            btPrintGatePassJeep, btPrintGatePassTrailer, btPrintGatePassCanter, btPrintGatePassTangul,
            btPrintGatePassSienna, btPrintGatePassJ5, btPrintGatePassWheelbarrow;
    Button btPrintLoadingCar, btPrintLoadingKeke, btPrintLoadingBus, btPrintLoadingHilux,
            btPrintLoadingJeep, btPrintLoadingTrailer, btPrintLoadingCanter, btPrintLoadingTangul,
            btPrintLoadingSienna, btPrintLoadingJ5, btPrintLoadingWheelbarrow;
    //Button btTransactionSummary;
    LinearLayout llAnimalCamel, llAnimalCow, llAnimalSheep, llAnimalGoat;
    LinearLayout llProduceMaize, llProduceBeans, llProduceRice, llProduceMillet, llProduceGroundnut;
    LinearLayout llFacilityShop, llFacilityStore, llFacilityRumfa;
    LinearLayout llGatePassCar, llGatePassKeke, llGatePassBus, llGatePassHilux,
            llGatePassJeep, llGatePassTrailer, llGatePassCanter, llGatePassTangul,
            llGatePassSienna, llGatePassJ5, llGatePassWheelbarrow;
    LinearLayout llLoadingCar, llLoadingKeke, llLoadingBus, llLoadingHilux,
            llLoadingJeep, llLoadingTrailer, llLoadingCanter, llLoadingTangul,
            llLoadingSienna, llLoadingJ5, llLoadingWheelbarrow;
    LinearLayout animalsCategory, produceCategory, gatePassCategory, loadingCategory, facilityCategory;

   private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load Storage
        userData = getSharedPreferences(DAILYSTORAGE, Context.MODE_PRIVATE);
        preferences = getSharedPreferences(LOCALSTORAGE, Context.MODE_PRIVATE);
        // set current Layout
        setContentView(R.layout.activity_main);

        //Keep Screen ON when the app is opened
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // Printer param
        handler = new HandlerUtils.MyHandler(iHandlerIntent);
        callback = new IPosPrinterCallback.Stub() {

            @Override
            public void onRunResult(final boolean isSuccess) throws RemoteException {
                Log.i(TAG,"result:" + isSuccess + "\n");
            }

            @Override
            public void onReturnString(final String value) throws RemoteException {
                Log.i(TAG,"result:" + value + "\n");
            }
        };
        Intent intent=new Intent();
        intent.setPackage("com.iposprinter.iposprinterservice");
        intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
        //startService(intent);
        bindService(intent, connectService, Context.BIND_AUTO_CREATE);

        IntentFilter printerStatusFilter = new IntentFilter();
        printerStatusFilter.addAction(PRINTER_NORMAL_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPERLESS_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPEREXISTS_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_NORMALTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_MOTOR_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_BUSY_ACTION);

        registerReceiver(IPosPrinterStatusListener, printerStatusFilter);
        //AWS
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        viewInit();

    }

    public String getDeviceName(){
        String day = new SimpleDateFormat("E").format(new Date());
        return preferences.getString(day, "null");
    }

    public void viewInit(){
        TextView tvDeviceName = findViewById(R.id.tvDeviceName);

        etCamelNo = findViewById(R.id.etCamelNo);
        etCowNo = findViewById(R.id.etCowNo);
        etSheepNo = findViewById(R.id.etSheepNo);
        etGoatNo = findViewById(R.id.etGoatNo);

        etMaizeNo = findViewById(R.id.etMaizeNo);
        etBeansNo = findViewById(R.id.etBeansNo);
        etRiceNo = findViewById(R.id.etRiceNo);
        etMilletNo = findViewById(R.id.etMilletNo);
        etGroundnutNo = findViewById(R.id.etGroundnutNo);

        btPrintCamel = findViewById(R.id.btPrintCamel);
        btPrintCow = findViewById(R.id.btPrintCow);
        btPrintSheep = findViewById(R.id.btPrintSheep);
        btPrintGoat = findViewById(R.id.btPrintGoat);



        btPrintMaize = findViewById(R.id.btPrintMaize);
        btPrintBeans = findViewById(R.id.btPrintBeans);
        btPrintRice = findViewById(R.id.btPrintRice);
        btPrintMillet = findViewById(R.id.btPrintMillet);
        btPrintGroundnut  = findViewById(R.id.btPrintGroundnut);

        btPrintFacilityShop = findViewById(R.id.btPrintFacilityShop);
        btPrintFacilityStore = findViewById(R.id.btPrintFacilityStore);
        btPrintFacilityRumfa = findViewById(R.id.btPrintFacilityRumfa);

        btPrintGatePassCar = findViewById(R.id.btPrintGatePassCar);
        btPrintGatePassKeke  = findViewById(R.id.btPrintGatePassKeke);
        btPrintGatePassBus = findViewById(R.id.btPrintGatePassBus);
        btPrintGatePassHilux  = findViewById(R.id.btPrintGatePassHilux);
        btPrintGatePassJeep = findViewById(R.id.btPrintGatePassJeep);
        btPrintGatePassTrailer  = findViewById(R.id.btPrintGatePassTrailer);
        btPrintGatePassCanter = findViewById(R.id.btPrintGatePassCanter);
        btPrintGatePassTangul = findViewById(R.id.btPrintGatePassTangul);
        btPrintGatePassSienna = findViewById(R.id.btPrintGatePassSienna);
        btPrintGatePassJ5 = findViewById(R.id.btPrintGatePassJ5);
        btPrintGatePassWheelbarrow = findViewById(R.id.btPrintGatePassWheelbarrow);

        btPrintLoadingCar = findViewById(R.id.btPrintLoadingCar);
        btPrintLoadingKeke  = findViewById(R.id.btPrintLoadingKeke);
        btPrintLoadingBus = findViewById(R.id.btPrintLoadingBus);
        btPrintLoadingHilux  = findViewById(R.id.btPrintLoadingHilux);
        btPrintLoadingJeep = findViewById(R.id.btPrintLoadingJeep);
        btPrintLoadingTrailer  = findViewById(R.id.btPrintLoadingTrailer);
        btPrintLoadingCanter = findViewById(R.id.btPrintLoadingCanter);
        btPrintLoadingTangul = findViewById(R.id.btPrintLoadingTangul);
        btPrintLoadingSienna = findViewById(R.id.btPrintLoadingSienna);
        btPrintLoadingJ5 = findViewById(R.id.btPrintLoadingJ5);
        btPrintLoadingWheelbarrow = findViewById(R.id.btPrintLoadingWheelbarrow);

        //btTransactionSummary = findViewById(R.id.btTransactionSummary);

        llAnimalCamel = findViewById(R.id.llAnimalCamel);
        llAnimalCow = findViewById(R.id.llAnimalCow);
        llAnimalSheep = findViewById(R.id.llAnimalSheep);
        llAnimalGoat = findViewById(R.id.llAnimalGoat);
        
        llProduceMaize = findViewById(R.id.llProduceMaize);
        llProduceBeans = findViewById(R.id.llProduceBeans);
        llProduceRice = findViewById(R.id.llProduceRice);
        llProduceMillet = findViewById(R.id.llProduceMillet);
        llProduceGroundnut = findViewById(R.id.llProduceGroundnut);

        llFacilityShop = findViewById(R.id.llFacilityShop);
        llFacilityStore = findViewById(R.id.llFacilityStore);
        llFacilityRumfa = findViewById(R.id.llFacilityRumfa);

        llGatePassCar = findViewById(R.id.llGatePassCar);
        llGatePassKeke  = findViewById(R.id.llGatePassKeke);
        llGatePassBus = findViewById(R.id.llGatePassBus);
        llGatePassHilux = findViewById(R.id.llGatePassHilux);
        llGatePassJeep  = findViewById(R.id.llGatePassJeep);
        llGatePassTrailer = findViewById(R.id.llGatePassTrailer);
        llGatePassCanter = findViewById(R.id.llGatePassCanter);
        llGatePassTangul = findViewById(R.id.llGatePassTangul);
        llGatePassSienna = findViewById(R.id.llGatePassSienna);
        llGatePassJ5 = findViewById(R.id.llGatePassJ5);
        llGatePassWheelbarrow = findViewById(R.id.llGatePassWheelbarrow);

        llLoadingCar = findViewById(R.id.llLoadingCar);
        llLoadingKeke  = findViewById(R.id.llLoadingKeke);
        llLoadingBus = findViewById(R.id.llLoadingBus);
        llLoadingHilux = findViewById(R.id.llLoadingHilux);
        llLoadingJeep  = findViewById(R.id.llLoadingJeep);
        llLoadingTrailer = findViewById(R.id.llLoadingTrailer);
        llLoadingCanter = findViewById(R.id.llLoadingCanter);
        llLoadingTangul = findViewById(R.id.llLoadingTangul);
        llLoadingSienna = findViewById(R.id.llLoadingSienna);
        llLoadingJ5 = findViewById(R.id.llLoadingJ5);
        llLoadingWheelbarrow = findViewById(R.id.llLoadingWheelbarrow);

        animalsCategory = findViewById(R.id.animalsCategory);
        produceCategory = findViewById(R.id.produceCategory);
        gatePassCategory = findViewById(R.id.gatePassCategory);
        loadingCategory = findViewById(R.id.loadingCategory);
        facilityCategory = findViewById(R.id.facilityCategory);



        // set OnClick Lister on The Buttons

        btPrintCamel.setOnClickListener(this);
        btPrintCow.setOnClickListener(this);
        btPrintSheep.setOnClickListener(this);
        btPrintGoat.setOnClickListener(this);

        btPrintMaize.setOnClickListener(this);
        btPrintBeans.setOnClickListener(this);
        btPrintRice.setOnClickListener(this);
        btPrintMillet.setOnClickListener(this);
        btPrintGroundnut.setOnClickListener(this);

        btPrintFacilityShop.setOnClickListener(this);
        btPrintFacilityStore.setOnClickListener(this);
        btPrintFacilityRumfa.setOnClickListener(this);

        btPrintGatePassCar.setOnClickListener(this);
        btPrintGatePassKeke.setOnClickListener(this);
        btPrintGatePassBus.setOnClickListener(this);
        btPrintGatePassHilux.setOnClickListener(this);
        btPrintGatePassJeep.setOnClickListener(this);
        btPrintGatePassTrailer.setOnClickListener(this);
        btPrintGatePassCanter.setOnClickListener(this);
        btPrintGatePassTangul.setOnClickListener(this);
        btPrintGatePassSienna.setOnClickListener(this);
        btPrintGatePassJ5.setOnClickListener(this);
        btPrintGatePassWheelbarrow.setOnClickListener(this);

        btPrintLoadingCar.setOnClickListener(this);
        btPrintLoadingKeke.setOnClickListener(this);
        btPrintLoadingBus.setOnClickListener(this);
        btPrintLoadingHilux.setOnClickListener(this);
        btPrintLoadingJeep.setOnClickListener(this);
        btPrintLoadingTrailer.setOnClickListener(this);
        btPrintLoadingCanter.setOnClickListener(this);
        btPrintLoadingTangul.setOnClickListener(this);
        btPrintLoadingSienna.setOnClickListener(this);
        btPrintLoadingJ5.setOnClickListener(this);
        btPrintLoadingWheelbarrow.setOnClickListener(this);

       // btTransactionSummary.setOnClickListener(this);

        animalsCategory.setOnClickListener(this);
        produceCategory.setOnClickListener(this);
        gatePassCategory.setOnClickListener(this);
        loadingCategory.setOnClickListener(this);

        if(!getDeviceName().equals("null")) {
            tvDeviceName.setText(getDeviceName());

            if (preferences.getBoolean("isProduce", false)) {
                produceCategory.setVisibility(View.VISIBLE);

                if (!preferences.getString("maize", "").isEmpty()) {
                    llProduceMaize.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("beans", "").isEmpty()) {
                    llProduceBeans.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("rice", "").isEmpty()) {
                    llProduceRice.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("millet", "").isEmpty()) {
                    llProduceMillet.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("groundnut", "").isEmpty()) {
                    llProduceGroundnut.setVisibility(View.VISIBLE);

                }
            }
            if (preferences.getBoolean("isAnimals", false)) {
                animalsCategory.setVisibility(View.VISIBLE);

                if (!preferences.getString("camel", "").isEmpty()) {
                    llAnimalCamel.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("cow", "").isEmpty()) {
                    llAnimalCow.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("goat", "").isEmpty()) {
                    llAnimalGoat.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("sheep", "").isEmpty()) {
                    llAnimalSheep.setVisibility(View.VISIBLE);

                }
            }
            if (preferences.getBoolean("isFacility", false)) {
                facilityCategory.setVisibility(View.VISIBLE);

                if (!preferences.getString("shop", "").isEmpty()) {
                    llFacilityShop.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("store", "").isEmpty()) {
                    llFacilityStore.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("rumfa", "").isEmpty()) {
                    llFacilityRumfa.setVisibility(View.VISIBLE);

                }

            }

            if (preferences.getBoolean("isGatePass", false)) {
                gatePassCategory.setVisibility(View.VISIBLE);

                if (!preferences.getString("gcar", "").isEmpty()) {
                    llGatePassCar.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gkeke", "").isEmpty()) {
                    llGatePassKeke.setVisibility(View.VISIBLE);


                }
                if (!preferences.getString("gbus", "").isEmpty()) {
                    llGatePassBus.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("ghilux", "").isEmpty()) {
                    llGatePassHilux.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gsienna", "").isEmpty()) {
                    llGatePassSienna.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gjeep", "").isEmpty()) {
                    llGatePassJeep.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gtrailer", "").isEmpty()) {
                    llGatePassTrailer.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gcanter", "").isEmpty()) {
                    llGatePassCanter.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gtangul", "").isEmpty()) {
                    llGatePassTangul.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gj5", "").isEmpty()) {
                    llGatePassJ5.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("gwheelbarrow", "").isEmpty()) {
                    llGatePassWheelbarrow.setVisibility(View.VISIBLE);

                }

            }
            if (preferences.getBoolean("isLoading", false)) {
                loadingCategory.setVisibility(View.VISIBLE);

                if (!preferences.getString("lcar", "").isEmpty()) {
                    llLoadingCar.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lkeke", "").isEmpty()) {
                    llLoadingKeke.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lbus", "").isEmpty()) {
                    llLoadingBus.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lhilux", "").isEmpty()) {
                    llLoadingHilux.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lsienna", "").isEmpty()) {
                    llLoadingSienna.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("ljeep", "").isEmpty()) {
                    llLoadingJeep.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("ltrailer", "").isEmpty()) {
                    llLoadingTrailer.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lcanter", "").isEmpty()) {
                    llLoadingCanter.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("ltangul", "").isEmpty()) {
                    llLoadingTangul.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lj5", "").isEmpty()) {
                    llLoadingJ5.setVisibility(View.VISIBLE);

                }
                if (!preferences.getString("lwheelbarrow", "").isEmpty()) {
                    llLoadingWheelbarrow.setVisibility(View.VISIBLE);

                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(IPosPrinterStatusListener);
        unbindService(connectService);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void printerInit(){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try{
                    mIPosPrinterService.printerInit(callback);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void printTicket(final String deviceName, final String category,
                            final String item, final int fee, final int quantity, final String timeStamp)
    {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);

                try {
                    mIPosPrinterService.printBitmap(1, 8, mBitmap, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText(getApplicationContext().getString(R.string.header1)+"\n", "ST", 32, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText(getApplicationContext().getString(R.string.header2)+"\n", "ST", 32, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("Device: "+deviceName+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("Ticket Type: "+category+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("Item: "+item+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("Quantity: "+quantity+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("Amount N"+String.valueOf(fee*quantity)+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                   mIPosPrinterService.PrintSpecFormatText("Date & Time: "+timeStamp+"\n", "ST", 24, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.PrintSpecFormatText("***Powered by ajisaq.com***\n", "ST", 24, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    bitmapRecycle(mBitmap);
                    mIPosPrinterService.printerPerformPrint(80,  callback);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public int getPrinterStatus(){

        Log.i(TAG,"***** printerStatus"+printerStatus);
        try{
            printerStatus = mIPosPrinterService.getPrinterStatus();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        Log.i(TAG,"#### printerStatus"+printerStatus);
        return  printerStatus;
    }


    private void printAndSaveInOut(final String deviceName, final String category,
                                  final String item, final int fee, final int quantity){

        AlertDialog.Builder printTicketDialog = new AlertDialog.Builder(MainActivity.this);
        printTicketDialog.setCancelable(false);
        printTicketDialog.setTitle("Print Ticket");
        printTicketDialog.setMessage("Choose In/Out to Print Ticket for "+item );
        printTicketDialog.setPositiveButton("In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            if(!preferences.getString(item.toLowerCase(), "").isEmpty()){
                int newFee = Integer.valueOf(preferences.getString(item.toLowerCase(), ""));
                String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                printerInit();
                printTicket(deviceName, category, item, newFee, quantity, now);
                runMutation(deviceName, category, item, newFee * quantity, now);
            }


            }
        }).setNegativeButton("Out ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!preferences.getString(item.toLowerCase()+"2", "").isEmpty()){
                            int newFee = Integer.valueOf(preferences.getString(item.toLowerCase()+"2", ""));
                            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                            printerInit();
                            printTicket(deviceName, category, item, newFee, quantity, now);
                            runMutation(deviceName, category, item, newFee * quantity, now);
                        }

                    }
        }).show();
    }

    private void printAndSave(final String deviceName, final String category,
                              final String item, final int fee, final int quantity){
        if(getPrinterStatus() == PRINTER_NORMAL){
            AlertDialog.Builder printTicketDialog = new AlertDialog.Builder(MainActivity.this);
            printTicketDialog.setCancelable(false);
            printTicketDialog.setTitle("Print Ticket");
            printTicketDialog.setMessage("Are you sure you want to Print Ticket for "+item );
            printTicketDialog.setPositiveButton("Yes Print", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {

                    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        printerInit();
                        printTicket(deviceName, category, item, fee, quantity, now);
                        runMutation(deviceName, category, item, fee * quantity, now);


                }
            })
                    .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Action for "Cancel".
                        }
                    }).show();


        }else{
            Toast.makeText(MainActivity.this, "Printer not ready ", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public void onClick(View v) {
        String deviceName, category, item;
        int fee, quantity;
        switch (v.getId()){
            case R.id.btPrintCamel:
                if(!etCamelNo.getText().toString().isEmpty()
                        && Integer.valueOf(etCamelNo.getText().toString()) > 0) {

                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.animalsCategory);
                    item = "Camel";
                    fee = Integer.valueOf(preferences.getString("camel", ""));
                    quantity = Integer.valueOf(etCamelNo.getText().toString());
                    printAndSaveInOut(deviceName,category,item,fee, quantity);
                    etCamelNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintCow:
                if(!etCowNo.getText().toString().isEmpty()
                        && Integer.valueOf(etCowNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.animalsCategory);
                    item = "Cow";
                    fee = Integer.valueOf(preferences.getString("cow", ""));
                    quantity = Integer.valueOf(etCowNo.getText().toString());
                    printAndSaveInOut(deviceName,category,item,fee, quantity);
                    etCowNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintSheep:
                if(!etSheepNo.getText().toString().isEmpty()
                        && Integer.valueOf(etSheepNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.animalsCategory);
                    item = "Sheep";
                    fee = Integer.valueOf(preferences.getString("sheep", ""));
                    quantity = Integer.valueOf(etSheepNo.getText().toString());
                    printAndSaveInOut(deviceName,category,item,fee, quantity);
                    etSheepNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }                break;
            case R.id.btPrintGoat:
                if(!etGoatNo.getText().toString().isEmpty()
                        && Integer.valueOf(etGoatNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.animalsCategory);
                    item = "Goat";
                    fee = Integer.valueOf(preferences.getString("goat", ""));
                    quantity = Integer.valueOf(etGoatNo.getText().toString());
                    printAndSaveInOut(deviceName,category,item,fee, quantity);
                    etGoatNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btPrintMaize:
                if(!etMaizeNo.getText().toString().isEmpty()
                        && Integer.valueOf(etMaizeNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.produceCategory);
                    item = "Maize";
                    fee = Integer.valueOf(preferences.getString("maize", ""));
                    quantity = Integer.valueOf(etMaizeNo.getText().toString());
                    printAndSave(deviceName,category,item,fee, quantity);
                    etMaizeNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintBeans:
                if(!etBeansNo.getText().toString().isEmpty()
                        && Integer.valueOf(etBeansNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.produceCategory);
                    item = "Beans";
                    fee = Integer.valueOf(preferences.getString("beans", ""));
                    quantity = Integer.valueOf(etBeansNo.getText().toString());
                    printAndSave(deviceName,category,item,fee, quantity);
                    etBeansNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintRice:
                if(!etRiceNo.getText().toString().isEmpty()
                        && Integer.valueOf(etRiceNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.produceCategory);
                    item = "Rice";
                    fee = Integer.valueOf(preferences.getString("rice", ""));
                    quantity = Integer.valueOf(etRiceNo.getText().toString());
                    printAndSave(deviceName,category,item,fee, quantity);
                    etRiceNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintMillet:
                if(!etMilletNo.getText().toString().isEmpty()
                        && Integer.valueOf(etMilletNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.produceCategory);
                    item = "Millet";
                    fee = Integer.valueOf(preferences.getString("millet", ""));
                    quantity = Integer.valueOf(etMilletNo.getText().toString());
                    printAndSave(deviceName,category,item,fee, quantity);
                    etMilletNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btPrintGroundnut:
                if(!etGroundnutNo.getText().toString().isEmpty()
                        && Integer.valueOf(etGroundnutNo.getText().toString()) > 0) {
                    deviceName = getDeviceName();
                    category = getApplicationContext().getString(R.string.produceCategory);
                    item = "Groundnut";
                    fee = Integer.valueOf(preferences.getString("groundnut", ""));
                    quantity = Integer.valueOf(etGroundnutNo.getText().toString());
                    printAndSave(deviceName,category,item,fee, quantity);
                    etGroundnutNo.setText("");
                }else{
                    Toast.makeText(this, "Invalid Value", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btPrintGatePassCar:
                fee = Integer.valueOf(preferences.getString("gcar", ""));
                item = "Car";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassKeke:
                fee = Integer.valueOf(preferences.getString("gkeke", ""));
                item = "Keke Napep";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassBus:
                fee = Integer.valueOf(preferences.getString("gbus", ""));
                item = "Bus";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassHilux:
                fee = Integer.valueOf(preferences.getString("ghilux", ""));
                item = "Hilux";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassJeep:
                fee = Integer.valueOf(preferences.getString("gjeep", ""));
                item = "Jeep";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassTrailer:
                fee = Integer.valueOf(preferences.getString("gtrailer", ""));
                item = "Trailer";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassCanter:
                fee = Integer.valueOf(preferences.getString("gcanter", ""));
                item = "Canter";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassTangul:
                fee = Integer.valueOf(preferences.getString("gtangul", ""));
                item = "Tangul";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassSienna:
                fee = Integer.valueOf(preferences.getString("gsienna", ""));
                item = "Sienna";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassJ5:
                fee = Integer.valueOf(preferences.getString("gj5", ""));
                item = "J5";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintGatePassWheelbarrow:
                fee = Integer.valueOf(preferences.getString("gwheelbarrow", ""));
                item = "Wheelbarrow";
                category = getApplicationContext().getString(R.string.gatePassCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;


            case R.id.btPrintLoadingCar:
                fee = Integer.valueOf(preferences.getString("lcar", ""));
                item = "Car";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingKeke:
                fee = Integer.valueOf(preferences.getString("lkeke", ""));
                item = "Keke Napep";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingBus:
                fee = Integer.valueOf(preferences.getString("lbus", ""));
                item = "Bus";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingHilux:
                fee = Integer.valueOf(preferences.getString("lhilux", ""));
                item = "Hilux";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingJeep:
                fee = Integer.valueOf(preferences.getString("ljeep", ""));
                item = "Jeep";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingTrailer:
                fee = Integer.valueOf(preferences.getString("ltrailer", ""));
                item = "Trailer";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingCanter:
                fee = Integer.valueOf(preferences.getString("lcanter", ""));
                item = "Canter";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingTangul:
                fee = Integer.valueOf(preferences.getString("ltangul", ""));
                item = "Tangul";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingSienna:
                fee = Integer.valueOf(preferences.getString("lsienna", ""));
                item = "Sienna";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingJ5:
                fee = Integer.valueOf(preferences.getString("lJ5", ""));
                item = "J5";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintLoadingWheelbarrow:
                fee = Integer.valueOf(preferences.getString("lwheelbarrow", ""));
                item = "Wheelbarrow";
                category = getApplicationContext().getString(R.string.loadingCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintFacilityShop:
                fee = Integer.valueOf(preferences.getString("shop", ""));
                item = "Shop";
                category = getApplicationContext().getString(R.string.facilityCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintFacilityStore:
                fee = Integer.valueOf(preferences.getString("store", ""));
                item = "Store";
                category = getApplicationContext().getString(R.string.facilityCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
            case R.id.btPrintFacilityRumfa:
                fee = Integer.valueOf(preferences.getString("rumfa", ""));
                item = "Rumfa";
                category = getApplicationContext().getString(R.string.facilityCategory);
                deviceName = getDeviceName();
                quantity = 1;
                printAndSave(deviceName,category,item,fee, quantity);
                break;
           /*/ case R.id.btTransactionSummary:

                startActivity(new Intent(getApplicationContext(), TransactionSummary.class));
                break; */
            default:
                break;
        }
    }


    //AWS runMutation runMutation(deviceName, category, item, (fee*quantity, now );
    public void runMutation(String deviceName, String category, String item,  int amount, String timeStamp){

        CreateFuforeTicketsInput createTestTicketTableInput = CreateFuforeTicketsInput.builder()
                .id(UUID.randomUUID().toString())
                .receiptType(category)
                .itemType(item)
                .fee(amount)
                .deviceName(deviceName)
                .date(timeStamp)
                .build();

        mAWSAppSyncClient.mutate(CreateFuforeTicketsMutation.builder().input(createTestTicketTableInput).build()).enqueue(mutationCallback);
        optimisticWrite(createTestTicketTableInput);
    }

    private GraphQLCall.Callback<CreateFuforeTicketsMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateFuforeTicketsMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateFuforeTicketsMutation.Data> response) {
            //Toast.makeText(getApplicationContext(), "Data uploaded", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            //Toast.makeText(getApplicationContext(), "Data upload Failed", Toast.LENGTH_LONG).show();
        }
    };

    private void optimisticWrite(CreateFuforeTicketsInput createTestTicketTableInput){
        final CreateFuforeTicketsMutation.CreateFuforeTickets expected =
                new CreateFuforeTicketsMutation.CreateFuforeTickets(
                        "",                          //GraphQL Type name
                        createTestTicketTableInput.id(),
                        createTestTicketTableInput.deviceName(),
                        createTestTicketTableInput.receiptType(),
                        createTestTicketTableInput.itemType(),
                        createTestTicketTableInput.fee(),
                        createTestTicketTableInput.date()
                );

        //final AWSAppSyncClient client = ClientFactory.appSyncClient();
        final ListFuforeTicketssQuery listFuforeTicketssQuery = ListFuforeTicketssQuery.builder().build();

        mAWSAppSyncClient.query(listFuforeTicketssQuery).responseFetcher(AppSyncResponseFetchers.CACHE_ONLY)
                .enqueue(new GraphQLCall.Callback<ListFuforeTicketssQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<ListFuforeTicketssQuery.Data> response) {
                        //Populate a copy of the query in the cache
                        List<ListFuforeTicketssQuery.Item> items = new ArrayList<>();
                        if(response.data() != null){
                            items.addAll(response.data().listFuforeTicketss().items());
                        }

                        //Add the newly created item to the cache copy
                        items.add(new ListFuforeTicketssQuery.Item(expected.__typename(),
                                expected.id(), expected.deviceName(), expected.receiptType(),  expected.itemType(), expected.fee(),  expected.date()));

                        //Overwrite the cache with the new results
                        ListFuforeTicketssQuery.Data data = new ListFuforeTicketssQuery.Data(new ListFuforeTicketssQuery.ListFuforeTicketss(
                                "", items, null
                        ));

                        mAWSAppSyncClient.getStore().write(listFuforeTicketssQuery, data).enqueue(null);
                        Log.i(TAG, "Successfully added item to local store");
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e(TAG, "Failed to add item ", e);
                    }
                });
    }
}
