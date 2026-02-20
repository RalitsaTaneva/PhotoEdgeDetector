# 🖼️ Photo Edge Detector

A Java-based image processing library designed to handle various graphical formats (JPEG, PNG, BMP) and apply advanced visual transformations. The library focus is on converting colored images to grayscale and detecting object edges using mathematical operators.

## 🚀 Key Features

* **Image Manipulation:** Convert any supported colored image into grayscale using the **Luminosity Method**.
* **Edge Detection:** Identify and highlight object boundaries within images using the professional **Sobel Operator**.
* **File System Management:** Robust system for loading single images or entire directories, and saving processed results back to the local storage.

## 🧠 Core Algorithms

The library implements two primary image processing techniques:

1.  **Luminosity Grayscale:** Instead of a simple average, this algorithm accounts for human perception by applying a weighted formula: `0.21 R + 0.72 G + 0.07 B`.
    
2.  **Sobel Edge Detection:** A discrete differentiation operator that computes an approximation of the gradient of the image intensity function. It combines Gaussian smoothing and differentiation to find edges effectively.
