package com.ledao.common.utils;

import com.alibaba.fastjson.JSONArray;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxz
 * @date 2022/7/14
 */
public class GisUtils {

    private static double EARTH_RADIUS = 6378137d;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lng1 圆心经度
     * @param lat1 圆心纬度
     * @param lng2 坐标经度
     * @param lat2 坐标纬度
     * @return 返回米
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;


        double dLat = (lat1 - lat2) * Math.PI / 180;
        double dLng = (lng1 - lng2) * Math.PI / 180;
        double a1 = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat2 * Math.PI / 180) * Math.cos(lat1 * Math.PI / 180) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
        double d = EARTH_RADIUS * c;
        double dis = Math.round(d);
        return dis;
    }

    /**
     * 判断一个点是否在圆形区域内
     *
     * @param radius 半径 米
     * @param lng1   圆心经度
     * @param lat1   圆心纬度
     * @param lng2   坐标经度
     * @param lat2   坐标纬度
     */
    public static boolean isInCircle(double lng1, double lat1, double lng2, double lat2, String radius) {
        return getDistance(lng1, lat1, lng2, lat2) <= Double.parseDouble(radius);
    }

    /**
     * 判断是否在多边形区域内
     *
     * @param pointLon 要判断的点的纵坐标
     * @param pointLat 要判断的点的横坐标
     * @param points   坐标数组
     * @return
     */
    public static boolean isInPolygon(double pointLon, double pointLat, JSONArray points) {
        // 将要判断的横纵坐标组成一个点
        Point2D.Double point = new Point.Double(pointLon, pointLat);
        // 将区域各顶点的横纵坐标放到一个点集合里面
        List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
        double polygonPoint_x = 0.0, polygonPoint_y = 0.0;
        int len = points.size();
        for (int i = 0; i < len; i++) {
            polygonPoint_x = points.getJSONObject(i).getDouble("x");
            polygonPoint_y = points.getJSONObject(i).getDouble("y");
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        return check(point, pointList);
    }

    /**
     * 一个点是否在多边形内
     *
     * @param point   要判断的点的横纵坐标
     * @param polygon 组成的顶点坐标集合
     * @return
     */
    private static boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();

        Point2D.Double first = polygon.get(0);
        // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // 测试指定的 Point2D 是否在 Shape 的边界内。
        return peneralPath.contains(point);
    }
}
