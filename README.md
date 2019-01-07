Extended Android Pose Estimation using Mace(Xiaomi Inference Computing Engine)
=======

![pose_bts_jimin](https://user-images.githubusercontent.com/17498974/50782188-b8960f00-12ea-11e9-8541-9fb28fa03d79.gif)
![pose_jumping_jacks](https://user-images.githubusercontent.com/17498974/50782454-643f5f00-12eb-11e9-88b8-01897e7e89ab.gif)


Inference Results (Model size About 8MB (.a + .bin) PCKh : About 83)
------

<table>

  <tr>
    <td>Mate 10</td>
    <td>About 40 ms</td>
  </tr>

  <tr>
    <td>A2 Lite</td>
    <td>About 170 ms</td>
  </tr>

  <tr>
    <td>Xiaomi Mi pad</td>
    <td>About 40 ms</td>
  </tr>

  <tr>
    <td>Device</td>
    <td>Inferene time</td>
  </tr>

  <tr>
    <td>Galaxy S8+</td>
    <td>About 60 ms</td>
  </tr>

  <tr>
    <td>Galaxy S8</td>
    <td>About 70 msms</td>
  </tr>

  <tr>
    <td>Galaxy S7</td>
    <td>About 100 ms(require to more testing)</td>
  </tr>

  <tr>
    <td>Galaxy S6</td>
    <td>110 ms</td>
  </tr>

</table>

#### Inference method
* Compute the more accurate x and y coordinate by weight averaging 3x3 Slide window array around it
without using the max x, y coordinate.
* Before weight averaging coordinate x, y 33, 48 after 33.23634, 48.47124
(If you reverse it to the display size of the phone, the difference becomes more bigger.)

This repository is implemented [PoseEstimationForMobile](https://github.com/edvardHua/PoseEstimationForMobile) (Thanks for authors).
**Model files were excluded(related to company).**
If you want to write a model file, please use the [PoseEstimationForMobile Model](https://github.com/edvardHua/PoseEstimationForMobile/tree/master/android_demo/demo_mace/macelibrary/src/main/cpp/model/armeabi-v7a).
or frozen_model.pb --> [Optimizing model](https://github.com/tensorflow/tensorflow/blob/master/tensorflow/python/tools/optimize_for_inference.py) --> Optimized_model.pb  --> [Converting Deployment model](https://mace.readthedocs.io/en/latest/user_guide/advanced_usage.html#deployment-file)
--> Finally Make model.a file.**

```
if you use this repository, convert model(s) to code
Example)
# model_graph_format: code
# model_data_format: code
builds
  ├── include
  │   └── mace
  │       └── public
  │           ├── mace_engine_factory.h
  │           └── cpm_v2.h
  └── model
      └── cpm-v2.a
```

PoseEstimationForMobile' repository is used [v0.9.0](https://github.com/XiaoMi/mace/blob/master/RELEASE.md#v090-2018-07-20) (2018-07-20)
So migration lastest version [v0.10.0](https://github.com/XiaoMi/mace/blob/master/RELEASE.md#v0100-2019-01-03) (2019-01-03).
**If you build a Mace library (libmace.a) from Xiaomi github (master or v0.10.0 later version), you need to re-write the code(*.cpp). and deleted OpenCV3 library**

#### v0.10.0 (2019-01-03) highlighting the change portion.
##### Improvements
1. Support mixing usage of CPU and GPU.
2. Support ONNX format.
3. Support ARM Linux development board.
4. Support CPU quantization.
5. Update DSP library.
6. Add `Depthwise Deconvolution` of Caffe.
7. Add documents about debug and benchmark.
8. Bug fixed.

##### Incompatible Changes
1. **Remove all APIs in mace_runtime.h**

##### New APIs
1. **Add GPUContext and GPUContextBuilder API.**
2. **Add MaceEngineConfig API.**
3. **Add MaceStatus API.**
4. MaceTensor support data format.

##### etc..

##### My project environment
* OS Platform and Distribution: macOS Mojave 10.14
* Android SDK version : 21 later (Camera2 API)
* Android NDK version : **16(If you higher NDK version(17, 18), you replace cmake arguments gnustl_static =>  c++_shared or c++_static. because higher version is not supported a gnustl_static)**
* Xiaomi version: v0.10.0
* TensorFlow version: master branch
* Python version: 3.6
* Bazel version (if compiling from source): 18 and 19
* GCC/Compiler version (if compiling from source): 5.4

##### Advanced model's option
* target_abis : armeabi-v7a
* model_graph_format : code
* model_data_format : code
* winograd : 4 (But there is no performance's improvement)
