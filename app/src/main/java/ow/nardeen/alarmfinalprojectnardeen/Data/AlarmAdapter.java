package ow.nardeen.alarmfinalprojectnardeen.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import ow.nardeen.alarmfinalprojectnardeen.AddAlarmActivity;
import ow.nardeen.alarmfinalprojectnardeen.R;

/**
 * شاشة لعرض شاشة الادابتر
 */
// وسيط من نوع ساعات
public class AlarmAdapter extends ArrayAdapter<AlarmClock> // تخصيص الوسيط (الادابتر) بس لنوع الساعات
// الادابتر هو المركز هو الي بنعطيه معطيات وبعرضهن
// يعني بالفاير بيس في معطيات هاد الادابتر بوخد المعطيات من الفاير بيس وبعرضهن
// تخزين المهام الي هي عبارة عن كائنات بمبنى معطيات الي هي مصفوفة من نوع مهمة

{
    public AlarmAdapter(@NonNull Context context) {
        super(context, R.layout.alarm_item);
    }
    // عرض معطيات بالواجهة

    // رقم المهمة - position
    // الي بحوي هاد الايتم - parent

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) // כאן בונים ממשק שמציג משימת "Item"
    {
        //بناء  واجهةالي بتعرض المهمة - item
        View vItem = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        TextView tvPhone = vItem.findViewById(R.id.tvPhone);
        TextView tvMessage = vItem.findViewById(R.id.tvMessage);
        TextView tvDate = vItem.findViewById(R.id.tvDate);
        TextView tvTime = vItem.findViewById(R.id.tvTime);
        Button btnEdit = vItem.findViewById(R.id.btnEdit);
        Button btnDelete = vItem.findViewById(R.id.btnDelete);
        TextView tvPriority = vItem.findViewById(R.id.tPriority);

        //todo arrange item and fix date and time format
        // باخد القيم تبعت المهمة وبحطهن بالحقول
        final AlarmClock alarmClock = getItem(position); //عملت كائن وبدي استخرج القيم الي الو عن طريق الposition الي الو
        // بناء الكائن بهدف استخراج القيم من الحقول
        tvPhone.setText(alarmClock.getPhNo());
        tvMessage.setText(alarmClock.getMessage());
        tvPriority.setText(alarmClock.getPriority()+""); // عشان يتحول لنص
        btnEdit.setOnClickListener(new View.OnClickListener() { //פעולה מתארמ מה קורה כשלוחצים על כפתור ה"edit"
            @Override
            public void onClick(View view) {
                                           // لانو هاي مش اكتيفيتي منستعمل كونتيكست
                Intent intent = new Intent(getContext(), AddAlarmActivity.class);
                intent.putExtra("toEdit", alarmClock); // هون ببعت مع الانتنت نفس الكائن مع نفس الصفات والمعطيات
                getContext().startActivity(intent); //  تشغيل الانتنت عشان ينقل من شاشة لشاشة
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() { // פעולה מתארמ מה קורה כשלוחצים על כפתור ה"delete"
            @Override
            public void onClick(View view)
            {
                FirebaseDatabase.getInstance().getReference()
                        .child("Alarm Clock").child(alarmClock.getOwner()).child(alarmClock.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) { //פעולה שבה מתבצעת הבדיקה אם העצם מסוג "Alarm clock" נמחק בהצלחה ולפיה מופיע אצלנו הודעה באמצטות חלון
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "not deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


        //get - بتستخرج القيمة من الحقل
        // set - يتم تحديث الصفة

        return vItem;

        // checkbox عشان نفحص اذا المهمة تمت

    }





}
