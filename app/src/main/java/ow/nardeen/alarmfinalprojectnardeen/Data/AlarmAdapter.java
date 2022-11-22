package ow.nardeen.alarmfinalprojectnardeen.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import ow.nardeen.alarmfinalprojectnardeen.R;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //بناء  واجهةالي بتعرض المهمة - item
        View vItem = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        TextView tvPhone = vItem.findViewById(R.id.tvPhone);
        TextView tvMessage = vItem.findViewById(R.id.tvMessage);
        TextView tvDate = vItem.findViewById(R.id.tvDate);
        TextView tvTime = vItem.findViewById(R.id.tvTime);

        // باخد القيم تبعت المهمة وبحطهن بالحقول
        final AlarmClock alarmClock = getItem(position); //عملت كائن وبدي استخرج القيم الي الو عن طريق الposition الي الو
        // بناء الكائن بهدف استخراج القيم من الحقول
        tvPhone.setText(alarmClock.getPhNo());
        tvMessage.setText(alarmClock.getMessage());


        //get - بتستخرج القيمة من الحقل
        // set - يتم تحديث الصفة

        return vItem;

        // checkbox عشان نفحص اذا المهمة تمت

    }



}