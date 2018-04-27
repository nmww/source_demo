package com.bn.lccx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
public class CityAdapter<T> extends BaseAdapter implements Filterable
{
private List<T> mObjects;//��������  ��������
private List<T> mObjects2;//��������  ƴ������
private final Object mLock = new Object();
private int mResource;//չʾ�������������ݵ�View Id
private int mDropDownResource;//�����������ݵ�Id
private int mFieldId = 0;//������ѡ��ID
private boolean mNotifyOnChange = true;
private Context mContext;//��ǰ�����Ķ��� - Activity
private ArrayList<T> mOriginalValues;//ԭʼ�����б�
private ArrayFilter mFilter;
private LayoutInflater mInflater;
public CityAdapter(Context context, int textViewResourceId, T[] objects,T[] objects2) 
{
    init(context, textViewResourceId, 0, Arrays.asList(objects),Arrays.asList(objects2));
}
public void add(T object) 
{
    if (mOriginalValues != null) 
    {
        synchronized (mLock)
        {
            mOriginalValues.add(object);
            if (mNotifyOnChange) notifyDataSetChanged();
        }
    }
    else 
    {
        mObjects.add(object);
        if (mNotifyOnChange) notifyDataSetChanged();
    }
}
public void insert(T object, int index) 
{
    if (mOriginalValues != null)
    {
        synchronized (mLock) 
        {
            mOriginalValues.add(index, object);
            if (mNotifyOnChange) notifyDataSetChanged();
        }
    }
    else
    {
        mObjects.add(index, object);
        if (mNotifyOnChange) notifyDataSetChanged();
    }
}
public void remove(T object)
{
    if (mOriginalValues != null) 
    {
        synchronized (mLock) 
        {
            mOriginalValues.remove(object);
        }
    }
    else
    {
        mObjects.remove(object);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
}
public void clear() //���б���ɾ�����е���Ϣ
{
    if (mOriginalValues != null) 
    {
        synchronized (mLock) 
        {
            mOriginalValues.clear();
        }
    } 
    else
    {
        mObjects.clear();
    }
    if (mNotifyOnChange) notifyDataSetChanged();
}
public void sort(Comparator<? super T> comparator)//����ָ���ıȽ������������е����ݽ�������
{
    Collections.sort(mObjects, comparator);
    if (mNotifyOnChange) notifyDataSetChanged();        
}
@Override
public void notifyDataSetChanged()
{
    super.notifyDataSetChanged();
    mNotifyOnChange = true;
}
//�����Զ��޸�
public void setNotifyOnChange(boolean notifyOnChange)
{
    mNotifyOnChange = notifyOnChange;
}
//������  -- ��ʼ��������Ϣ
private void init(Context context, int resource, int textViewResourceId, List<T> objects ,List<T> objects2) 
{
    mContext = context;
    mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mResource = mDropDownResource = resource;
    mObjects = objects;
    mObjects2 = objects2;
    mFieldId = textViewResourceId;
}
//��������������������������Ķ���
public Context getContext()
{
    return mContext;
}
public int getCount() //����  �������ƺ���  �б�Ĵ�С
{
    return mObjects.size();
}

public T getItem(int position)//���س������ƺ����б���ָ��λ�õ��ַ�����ֵ
{
    return mObjects.get(position);
}
public int getPosition(T item)//���� �������ƺ��� �б���ָ���� �ַ���ֵ ������
{
    return mObjects.indexOf(item);
}
public long getItemId(int position)//��int�������Գ����ͷ���
{
    return position;
}
public View getView(int position, View convertView, ViewGroup parent)//����View
{
    return createViewFromResource(position, convertView, parent, mResource);
}
private View createViewFromResource(int position, View convertView, ViewGroup parent,//����View
        int resource)
{
    View view;
    TextView text;
    if (convertView == null) //�����ǰΪ��
    {
        view = mInflater.inflate(resource, parent, false);
    } 
    else //�����Ϊ��
    {
        view = convertView;
    }

    try {
        if (mFieldId == 0) //�����ǰ��Ϊ��,�ٶ����е���Դ����һ��TextView
        {
            text = (TextView) view;
        }
        else//����,�ڽ������ҵ�TextView
        {
            text = (TextView) view.findViewById(mFieldId);
        }
    } 
    catch (ClassCastException e) //�쳣����
    {
        throw new IllegalStateException
        (
           "ArrayAdapter requires the resource ID to be a TextView", e
        );
    }
    text.setText(getItem(position).toString());//ΪText��ֵ  -���ص�ǰ�������ƺ����б���ѡ�е�ֵ 
    return view;
}
public void setDropDownViewResource(int resource) //����������ͼ
{
    this.mDropDownResource = resource;
}
@Override
public View getDropDownView(int position, View convertView, ViewGroup parent) 
{
    return createViewFromResource(position, convertView, parent, mDropDownResource);
}
public static ArrayAdapter<CharSequence> createFromResource(Context context,//���ⲿ��Դ�д����µ�����������
        int textArrayResId, int textViewResId) 
{
    CharSequence[] strings = context.getResources().getTextArray(textArrayResId);//�����ַ�������
    return new ArrayAdapter<CharSequence>(context, textViewResId, strings);//��������������
}
public Filter getFilter() //�õ�������
{
    if (mFilter == null)//���Ϊ��,�������������
    {
        mFilter = new ArrayFilter();
    }
    return mFilter;
}
//�������������������������ָ����ǰ׺��ͷ,������ṩ��ǰ׺��ƥ��,�������ɾ��
private class ArrayFilter extends Filter 
{
    @Override
    protected FilterResults performFiltering(CharSequence prefix)//ִ�й���
    {
        FilterResults results = new FilterResults();//����FilterResults����
        if (mOriginalValues == null) //���Ϊ��
        {
            synchronized (mLock)
            {
                mOriginalValues = new ArrayList<T>(mObjects);
            }
        }
        if (prefix == null || prefix.length() == 0) 
        {
            synchronized (mLock) 
            {
                ArrayList<T> list = new ArrayList<T>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            }
        } 
        else 
        {
            String prefixString = prefix.toString().toLowerCase();//ת����Сд
            final ArrayList<T> values = mOriginalValues;
            final int count = values.size();
            final ArrayList<T> newValues = new ArrayList<T>(count);
            for (int i = 0; i < count; i++)
            {
                final T value = values.get(i);
                final String valueText = value.toString().toLowerCase();

                final T value2 = mObjects2.get(i);
                final String valueText2 = value2.toString().toLowerCase();
                
                //����ƴ�� 
                if(valueText2.startsWith(prefixString))
                {
                        newValues.add(value);
                }//���Һ���       
                else if(valueText.startsWith(prefixString))
                {
                        newValues.add(value);
                }
                else
                {      //��Ӻ��ֹ���
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;
                        for (int k = 0; k < wordCount; k++) 
                        {
	                        if (words[k].startsWith(prefixString)) 
	                        {
	                            newValues.add(value);
	                            break;
	                        }
                        }
                        //���ƴ����������
                        final String[] words2 = valueText2.split(" ");
                        final int wordCount2 = words2.length;

                    for (int k = 0; k < wordCount2; k++) {
                        if (words2[k].startsWith(prefixString))
                        {
                            newValues.add(value);
                            break;
                        }
                    }  
                }
            }
            results.values = newValues;
            results.count = newValues.size();
        }
        return results;
    }
    @SuppressWarnings("unchecked")
	protected void publishResults(CharSequence constraint, FilterResults results) 
    {    
        mObjects = (List<T>) results.values;
        if (results.count > 0)
        {
            notifyDataSetChanged();
        } else
        {
            notifyDataSetInvalidated();
        }
    }
}
}