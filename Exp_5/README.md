# 实验5_TensorFlow Lite 模型生成
---
## 实验内容
* 了解机器学习基础
* 了解TensorFlow及TensorFlow Lite
* 按照[教程](https://blog.csdn.net/llfjfz/article/details/124926231)完成基于TensorFlow Lite Model Maker的花卉模型生成
* 使用实验三的应用验证生成的模型
* 将上述完成的Jupyter Notebook在Github上进行共享

## TensorFlow Lite
* TensorFlow：一个核心开源库，可以快速的开发和训练机器学习模型。通过直接在浏览器中运行 Colab 笔记本来快速上手。
* TensorFlow = Tensor+Flow。Tensor代表“张量”，是一种多维数组结构；Flow代表“流”，TensorFlow采用数据流的进行处理。
* TensorFlow Lite是移动机器学习库，用于在移动设备、微控制器以及其他终端设备上部署模型。

## TensorFlow Lite Model Maker生成模型
借助TensorFlow Lite Model Maker库，可以简化使用自定义数据集训练TensorFlow Lite模型的过程。该库使用迁移学习来减少所需的训练数据量并缩短训练时间。

## 预备工作
首先安装程序运行必备的一些库。


```python
!pip install tflite-model-maker
```

    Requirement already satisfied: tflite-model-maker in f:\program files\python37\lib\site-packages (0.3.4)
    Requirement already satisfied: pillow>=7.0.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (8.3.1)
    Requirement already satisfied: tensorflow-datasets>=2.1.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (4.5.2)
    Requirement already satisfied: numpy>=1.17.3 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.21.6)
    Requirement already satisfied: librosa==0.8.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.8.1)
    Requirement already satisfied: tensorflow-addons>=0.11.2 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.17.0)
    Requirement already satisfied: urllib3!=1.25.0,!=1.25.1,<1.26,>=1.21.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.25.11)
    Requirement already satisfied: six>=1.12.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.16.0)
    Requirement already satisfied: tf-models-official==2.3.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (2.3.0)
    Requirement already satisfied: sentencepiece>=0.1.91 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.1.96)
    Requirement already satisfied: PyYAML>=5.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (6.0)
    Requirement already satisfied: tensorflow-hub<0.13,>=0.7.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.12.0)
    Requirement already satisfied: fire>=0.3.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.4.0)
    Requirement already satisfied: tensorflowjs>=2.4.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (3.18.0)
    Requirement already satisfied: Cython>=0.29.13 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.29.30)
    Requirement already satisfied: tensorflow-model-optimization>=0.5 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.7.2)
    Requirement already satisfied: tensorflow>=2.6.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (2.9.1)
    Requirement already satisfied: tflite-support>=0.3.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.4.0)
    Requirement already satisfied: neural-structured-learning>=1.3.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.3.1)
    Requirement already satisfied: matplotlib<3.5.0,>=3.0.3 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (3.4.2)
    Requirement already satisfied: numba==0.53 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (0.53.0)
    Requirement already satisfied: lxml>=4.6.1 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (4.8.0)
    Requirement already satisfied: flatbuffers==1.12 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.12)
    Requirement already satisfied: absl-py>=0.10.0 in f:\program files\python37\lib\site-packages (from tflite-model-maker) (1.0.0)
    Requirement already satisfied: scikit-learn!=0.19.0,>=0.14.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (0.24.2)
    Requirement already satisfied: audioread>=2.0.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (2.1.9)
    Requirement already satisfied: soundfile>=0.10.2 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (0.10.3.post1)
    Requirement already satisfied: joblib>=0.14 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (1.0.1)
    Requirement already satisfied: packaging>=20.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (20.9)
    Requirement already satisfied: decorator>=3.0.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (5.1.1)
    Requirement already satisfied: resampy>=0.2.2 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (0.2.2)
    Requirement already satisfied: pooch>=1.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (1.6.0)
    Requirement already satisfied: scipy>=1.0.0 in f:\program files\python37\lib\site-packages (from librosa==0.8.1->tflite-model-maker) (1.7.0)
    Requirement already satisfied: llvmlite<0.37,>=0.36.0rc1 in f:\program files\python37\lib\site-packages (from numba==0.53->tflite-model-maker) (0.36.0)
    Requirement already satisfied: setuptools in f:\program files\python37\lib\site-packages (from numba==0.53->tflite-model-maker) (57.4.0)
    Requirement already satisfied: kaggle>=1.3.9 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (1.5.12)
    Requirement already satisfied: py-cpuinfo>=3.3.0 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (8.0.0)
    Requirement already satisfied: pandas>=0.22.0 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (1.3.0)
    Requirement already satisfied: tf-slim>=1.1.0 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (1.1.0)
    Requirement already satisfied: opencv-python-headless in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (4.5.5.64)
    Requirement already satisfied: google-cloud-bigquery>=0.31.0 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (3.1.0)
    Requirement already satisfied: psutil>=5.4.3 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (5.9.0)
    Requirement already satisfied: google-api-python-client>=1.6.7 in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (2.48.0)
    Requirement already satisfied: dataclasses in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (0.6)
    Requirement already satisfied: gin-config in f:\program files\python37\lib\site-packages (from tf-models-official==2.3.0->tflite-model-maker) (0.5.0)
    Requirement already satisfied: termcolor in f:\program files\python37\lib\site-packages (from fire>=0.3.1->tflite-model-maker) (1.1.0)
    Requirement already satisfied: python-dateutil>=2.7 in f:\program files\python37\lib\site-packages (from matplotlib<3.5.0,>=3.0.3->tflite-model-maker) (2.8.2)
    Requirement already satisfied: pyparsing>=2.2.1 in f:\program files\python37\lib\site-packages (from matplotlib<3.5.0,>=3.0.3->tflite-model-maker) (2.4.7)
    Requirement already satisfied: cycler>=0.10 in f:\program files\python37\lib\site-packages (from matplotlib<3.5.0,>=3.0.3->tflite-model-maker) (0.10.0)
    Requirement already satisfied: kiwisolver>=1.0.1 in f:\program files\python37\lib\site-packages (from matplotlib<3.5.0,>=3.0.3->tflite-model-maker) (1.3.1)
    Requirement already satisfied: attrs in f:\program files\python37\lib\site-packages (from neural-structured-learning>=1.3.1->tflite-model-maker) (21.4.0)
    Requirement already satisfied: astunparse>=1.6.0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (1.6.3)
    Requirement already satisfied: gast<=0.4.0,>=0.2.1 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (0.4.0)
    Requirement already satisfied: h5py>=2.9.0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (3.3.0)
    Requirement already satisfied: libclang>=13.0.0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (14.0.1)
    Requirement already satisfied: protobuf<3.20,>=3.9.2 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (3.19.4)
    Requirement already satisfied: google-pasta>=0.1.1 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (0.2.0)
    Requirement already satisfied: grpcio<2.0,>=1.24.3 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (1.46.3)
    Requirement already satisfied: tensorflow-estimator<2.10.0,>=2.9.0rc0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (2.9.0)
    Requirement already satisfied: keras-preprocessing>=1.1.1 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (1.1.2)
    Requirement already satisfied: typing-extensions>=3.6.6 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (3.10.0.0)
    Requirement already satisfied: opt-einsum>=2.3.2 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (3.3.0)
    Requirement already satisfied: wrapt>=1.11.0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (1.12.1)
    Requirement already satisfied: keras<2.10.0,>=2.9.0rc0 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (2.9.0)
    Requirement already satisfied: tensorflow-io-gcs-filesystem>=0.23.1 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (0.26.0)
    Requirement already satisfied: tensorboard<2.10,>=2.9 in f:\program files\python37\lib\site-packages (from tensorflow>=2.6.0->tflite-model-maker) (2.9.0)
    Requirement already satisfied: typeguard>=2.7 in f:\program files\python37\lib\site-packages (from tensorflow-addons>=0.11.2->tflite-model-maker) (2.13.3)
    Requirement already satisfied: tqdm in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (4.62.0)
    Requirement already satisfied: promise in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (2.3)
    Requirement already satisfied: tensorflow-metadata in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (1.8.0)
    Requirement already satisfied: importlib-resources in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (5.7.0)
    Requirement already satisfied: dill in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (0.3.5.1)
    Requirement already satisfied: requests>=2.19.0 in f:\program files\python37\lib\site-packages (from tensorflow-datasets>=2.1.0->tflite-model-maker) (2.25.1)
    Requirement already satisfied: dm-tree~=0.1.1 in f:\program files\python37\lib\site-packages (from tensorflow-model-optimization>=0.5->tflite-model-maker) (0.1.7)
    Requirement already satisfied: sounddevice>=0.4.4 in f:\program files\python37\lib\site-packages (from tflite-support>=0.3.1->tflite-model-maker) (0.4.4)
    Requirement already satisfied: pybind11>=2.6.0 in f:\program files\python37\lib\site-packages (from tflite-support>=0.3.1->tflite-model-maker) (2.9.2)
    Requirement already satisfied: wheel<1.0,>=0.23.0 in f:\program files\python37\lib\site-packages (from astunparse>=1.6.0->tensorflow>=2.6.0->tflite-model-maker) (0.36.2)
    Requirement already satisfied: uritemplate<5,>=3.0.1 in f:\program files\python37\lib\site-packages (from google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (4.1.1)
    Requirement already satisfied: httplib2<1dev,>=0.15.0 in f:\program files\python37\lib\site-packages (from google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (0.20.4)
    Requirement already satisfied: google-auth<3.0.0dev,>=1.16.0 in f:\program files\python37\lib\site-packages (from google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (2.6.6)
    Requirement already satisfied: google-auth-httplib2>=0.1.0 in f:\program files\python37\lib\site-packages (from google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (0.1.0)
    Requirement already satisfied: google-api-core!=2.0.*,!=2.1.*,!=2.2.*,!=2.3.0,<3.0.0dev,>=1.31.5 in f:\program files\python37\lib\site-packages (from google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (2.8.0)
    Requirement already satisfied: google-cloud-core<3.0.0dev,>=1.4.1 in f:\program files\python37\lib\site-packages (from google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (2.3.0)
    Requirement already satisfied: google-resumable-media<3.0dev,>=0.6.0 in f:\program files\python37\lib\site-packages (from google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (2.3.3)
    Requirement already satisfied: google-cloud-bigquery-storage<3.0.0dev,>=2.0.0 in f:\program files\python37\lib\site-packages (from google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (2.13.1)
    Requirement already satisfied: pyarrow<9.0dev,>=3.0.0 in f:\program files\python37\lib\site-packages (from google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (8.0.0)
    Requirement already satisfied: proto-plus>=1.15.0 in f:\program files\python37\lib\site-packages (from google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (1.20.4)
    Requirement already satisfied: cached-property in f:\program files\python37\lib\site-packages (from h5py>=2.9.0->tensorflow>=2.6.0->tflite-model-maker) (1.5.2)
    Requirement already satisfied: certifi in f:\program files\python37\lib\site-packages (from kaggle>=1.3.9->tf-models-official==2.3.0->tflite-model-maker) (2021.5.30)
    Requirement already satisfied: python-slugify in f:\program files\python37\lib\site-packages (from kaggle>=1.3.9->tf-models-official==2.3.0->tflite-model-maker) (6.1.2)
    Requirement already satisfied: pytz>=2017.3 in f:\program files\python37\lib\site-packages (from pandas>=0.22.0->tf-models-official==2.3.0->tflite-model-maker) (2021.1)
    Requirement already satisfied: appdirs>=1.3.0 in f:\program files\python37\lib\site-packages (from pooch>=1.0->librosa==0.8.1->tflite-model-maker) (1.4.4)
    Requirement already satisfied: idna<3,>=2.5 in f:\program files\python37\lib\site-packages (from requests>=2.19.0->tensorflow-datasets>=2.1.0->tflite-model-maker) (2.10)
    Requirement already satisfied: chardet<5,>=3.0.2 in f:\program files\python37\lib\site-packages (from requests>=2.19.0->tensorflow-datasets>=2.1.0->tflite-model-maker) (4.0.0)
    Requirement already satisfied: threadpoolctl>=2.0.0 in f:\program files\python37\lib\site-packages (from scikit-learn!=0.19.0,>=0.14.0->librosa==0.8.1->tflite-model-maker) (2.2.0)
    Requirement already satisfied: CFFI>=1.0 in f:\program files\python37\lib\site-packages (from sounddevice>=0.4.4->tflite-support>=0.3.1->tflite-model-maker) (1.15.0)
    Requirement already satisfied: markdown>=2.6.8 in f:\program files\python37\lib\site-packages (from tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (3.3.4)
    Requirement already satisfied: tensorboard-data-server<0.7.0,>=0.6.0 in f:\program files\python37\lib\site-packages (from tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (0.6.1)
    Requirement already satisfied: werkzeug>=1.0.1 in f:\program files\python37\lib\site-packages (from tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (2.0.1)
    Requirement already satisfied: google-auth-oauthlib<0.5,>=0.4.1 in f:\program files\python37\lib\site-packages (from tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (0.4.6)
    Requirement already satisfied: tensorboard-plugin-wit>=1.6.0 in f:\program files\python37\lib\site-packages (from tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (1.8.1)
    Requirement already satisfied: zipp>=3.1.0 in f:\program files\python37\lib\site-packages (from importlib-resources->tensorflow-datasets>=2.1.0->tflite-model-maker) (3.5.0)
    Requirement already satisfied: googleapis-common-protos<2,>=1.52.0 in f:\program files\python37\lib\site-packages (from tensorflow-metadata->tensorflow-datasets>=2.1.0->tflite-model-maker) (1.56.1)
    Requirement already satisfied: colorama in f:\program files\python37\lib\site-packages (from tqdm->tensorflow-datasets>=2.1.0->tflite-model-maker) (0.4.4)
    Requirement already satisfied: pycparser in f:\program files\python37\lib\site-packages (from CFFI>=1.0->sounddevice>=0.4.4->tflite-support>=0.3.1->tflite-model-maker) (2.21)
    Requirement already satisfied: grpcio-status<2.0dev,>=1.33.2 in f:\program files\python37\lib\site-packages (from google-api-core!=2.0.*,!=2.1.*,!=2.2.*,!=2.3.0,<3.0.0dev,>=1.31.5->google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (1.46.3)
    Requirement already satisfied: cachetools<6.0,>=2.0.0 in f:\program files\python37\lib\site-packages (from google-auth<3.0.0dev,>=1.16.0->google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (5.1.0)
    Requirement already satisfied: pyasn1-modules>=0.2.1 in f:\program files\python37\lib\site-packages (from google-auth<3.0.0dev,>=1.16.0->google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (0.2.8)
    Requirement already satisfied: rsa<5,>=3.1.4 in f:\program files\python37\lib\site-packages (from google-auth<3.0.0dev,>=1.16.0->google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (4.8)
    Requirement already satisfied: requests-oauthlib>=0.7.0 in f:\program files\python37\lib\site-packages (from google-auth-oauthlib<0.5,>=0.4.1->tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (1.3.1)
    Requirement already satisfied: google-crc32c<2.0dev,>=1.0 in f:\program files\python37\lib\site-packages (from google-resumable-media<3.0dev,>=0.6.0->google-cloud-bigquery>=0.31.0->tf-models-official==2.3.0->tflite-model-maker) (1.3.0)
    Requirement already satisfied: importlib-metadata in f:\program files\python37\lib\site-packages (from markdown>=2.6.8->tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (4.6.1)
    Requirement already satisfied: text-unidecode>=1.3 in f:\program files\python37\lib\site-packages (from python-slugify->kaggle>=1.3.9->tf-models-official==2.3.0->tflite-model-maker) (1.3)
    Requirement already satisfied: pyasn1<0.5.0,>=0.4.6 in f:\program files\python37\lib\site-packages (from pyasn1-modules>=0.2.1->google-auth<3.0.0dev,>=1.16.0->google-api-python-client>=1.6.7->tf-models-official==2.3.0->tflite-model-maker) (0.4.8)
    Requirement already satisfied: oauthlib>=3.0.0 in f:\program files\python37\lib\site-packages (from requests-oauthlib>=0.7.0->google-auth-oauthlib<0.5,>=0.4.1->tensorboard<2.10,>=2.9->tensorflow>=2.6.0->tflite-model-maker) (3.2.0)


    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: Ignoring invalid distribution -ip (f:\program files\python37\lib\site-packages)
    WARNING: You are using pip version 22.0.4; however, version 22.1.2 is available.
    You should consider upgrading via the 'f:\Program Files\Python37\python.exe -m pip install --upgrade pip' command.



接下来导入相关库


```python
import os

import numpy as np

import tensorflow as tf
assert tf.__version__.startswith('2')

from tflite_model_maker import model_spec
from tflite_model_maker import image_classifier
from tflite_model_maker.config import ExportFormat
from tflite_model_maker.config import QuantizationConfig
from tflite_model_maker.image_classifier import DataLoader

import matplotlib.pyplot as plt
```

## 模型训练
### 获取数据
本次实验先从较小的数据集开始训练，当然越多的数据，模型精度更高。


```python
image_path = tf.keras.utils.get_file(
      'flower_photos.tgz',
      'https://storage.googleapis.com/download.tensorflow.org/example_images/flower_photos.tgz',
      extract=True)
image_path = os.path.join(os.path.dirname(image_path), 'flower_photos')
```

这里从`storage.googleapis.com`中下载了本实验所需要的数据集。`image_path`可以定制，默认是在用户目录的`.keras\datasets`中。

### 运行示例
一共需4步完成。

**第一步：加载数据集，并将数据集分为训练数据和测试数据。**


```python
data = DataLoader.from_folder(image_path)
train_data, test_data = data.split(0.9)
```

    INFO:tensorflow:Load image with size: 3670, num_label: 5, labels: daisy, dandelion, roses, sunflowers, tulips.


**第二步：训练Tensorflow模型**


```python
inception_v3_spec = image_classifier.ModelSpec(uri='https://storage.googleapis.com/tfhub-modules/tensorflow/efficientnet/lite0/feature-vector/2.tar.gz')
inception_v3_spec.input_image_shape = [240, 240]
model = image_classifier.create(train_data, model_spec=inception_v3_spec)
```

    INFO:tensorflow:Retraining the models...
    Model: "sequential"
    _________________________________________________________________
     Layer (type)                Output Shape              Param #   
    =================================================================
     hub_keras_layer_v1v2 (HubKe  (None, 1280)             3413024   
     rasLayerV1V2)                                                   
                                                                     
     dropout (Dropout)           (None, 1280)              0         
                                                                     
     dense (Dense)               (None, 5)                 6405      
                                                                     
    =================================================================
    Total params: 3,419,429
    Trainable params: 6,405
    Non-trainable params: 3,413,024
    _________________________________________________________________
    None


    f:\Program Files\Python37\lib\site-packages\keras\optimizers\optimizer_v2\gradient_descent.py:108: UserWarning: The `lr` argument is deprecated, use `learning_rate` instead.
      super(SGD, self).__init__(name, **kwargs)


    Epoch 1/5
    103/103 [==============================] - 51s 463ms/step - loss: 0.8806 - accuracy: 0.7627
    Epoch 2/5
    103/103 [==============================] - 50s 481ms/step - loss: 0.6671 - accuracy: 0.8865
    Epoch 3/5
    103/103 [==============================] - 55s 532ms/step - loss: 0.6358 - accuracy: 0.9075
    Epoch 4/5
    103/103 [==============================] - 65s 631ms/step - loss: 0.6162 - accuracy: 0.9196
    Epoch 5/5
    103/103 [==============================] - 65s 633ms/step - loss: 0.5990 - accuracy: 0.9290


**第三步：评估模型**


```python
loss, accuracy = model.evaluate(test_data)
```

    12/12 [==============================] - 8s 497ms/step - loss: 0.5982 - accuracy: 0.9264


**第四步，导出Tensorflow Lite模型**


```python
model.export(export_dir='.')
```

    INFO:tensorflow:Assets written to: C:\Users\lenovo\AppData\Local\Temp\tmpcru05po9\assets


    INFO:tensorflow:Assets written to: C:\Users\lenovo\AppData\Local\Temp\tmpcru05po9\assets
    f:\Program Files\Python37\lib\site-packages\tensorflow\lite\python\convert.py:766: UserWarning: Statistics for quantized inputs were expected, but not specified; continuing anyway.
      warnings.warn("Statistics for quantized inputs were expected, but not "


    INFO:tensorflow:Label file is inside the TFLite model with metadata.


    INFO:tensorflow:Label file is inside the TFLite model with metadata.


    INFO:tensorflow:Saving labels in C:\Users\lenovo\AppData\Local\Temp\tmpfddjai9e\labels.txt


    INFO:tensorflow:Saving labels in C:\Users\lenovo\AppData\Local\Temp\tmpfddjai9e\labels.txt


    INFO:tensorflow:TensorFlow Lite model exported successfully: .\model.tflite


    INFO:tensorflow:TensorFlow Lite model exported successfully: .\model.tflite


这里导出的Tensorflow Lite模型包含了元数据(metadata),其能够提供标准的模型描述。导出的模型存放在Jupyter Notebook当前的工作目录中。

参考文档:https://blog.csdn.net/llfjfz/article/details/124926231

## 验证生成的模型
### 将model.tflite替换实验三的FlowerModel.tflite
实验结果
![image-20220505213724201](README.assets/3.png)

**至此本次实验完成**

