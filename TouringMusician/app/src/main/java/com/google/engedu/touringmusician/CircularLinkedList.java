/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    Node head;

    private void insertFirst(Node node) {
        head = node;
        head.prev = head;
        head.next = head;
    }

    private void insertNode(Node node, Node curr) {
        node.next = curr;
        node.prev = curr.prev;
        curr.prev = node;
        node.prev.next = node;
        
    }

    public void insertBeginning(Point p) {
        Node newNode = new Node();
        newNode.point = p;

        if(this.head == null) {
            insertFirst(newNode);
        }
        else {
            insertNode(newNode, head);
            this.head = newNode;
        }
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;

        Point prevPoint;
        Node curr = head;
        // If not empty or only has 1 node
        if(head.next != head) {
            prevPoint = curr.point;
            do {
                curr = curr.next;
                total += distanceBetween(prevPoint, curr.point);
                prevPoint = curr.point;
            }
            while (curr != head);
        }

        return total;
    }

    public void insertNearest(Point p) {
        Node node = new Node();
        node.point = p;

        Node nearest = null;
        float nearestDist = Float.POSITIVE_INFINITY;
        Node curr = this.head;

        if(this.head == null)
            insertFirst(node);
        else {
            do {
                curr = curr.next;
                if(distanceBetween(curr.point, p) < nearestDist) {
                    nearestDist = distanceBetween(curr.point, p);
                    nearest = curr;
                }
            }
            while (curr != head);

            if(distanceBetween(p, nearest.prev.point) < distanceBetween(p, nearest.next.point))
                insertNode(node, nearest);
            else
                insertNode(node, nearest.next);
        }

    }

    public void insertSmallest(Point p) {
        Node node = new Node();
        node.point = p;
        Node best = null;
        Node curr = head;
        double distance = Double.POSITIVE_INFINITY;
        if(head == null) {
            insertFirst(node);
        }
        else if(head.next == head) {
            insertNode(node, head);
        }
        else {
            do {
                double dist = totalDistance() - distanceBetween(curr.point, curr.next.point);
                dist += distanceBetween(p, curr.point) + distanceBetween(p, curr.next.point);
                if(dist < distance) {
                    distance = dist;
                    best = curr.next;
                }
                curr = curr.next;
            }
            while (curr != head);
            insertNode(node, best);
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
